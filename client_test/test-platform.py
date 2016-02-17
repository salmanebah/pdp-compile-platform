#! /usr/bin/env python

from suds.client import Client
import sys
import time
import logging

client_serveur = """import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Problem {

class UppercaseUDPClient {
/**
 * asks for the Echo daemon of host in port "port"
  * public class Problem
 * @param host
 */
 public  String getEcho(String host, int port, String message) {
    DatagramSocket socket =null;
            try {
                socket = new DatagramSocket();
                byte[] buf = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length,
InetAddress.getByName(host), port);
                socket.send(packet);
                byte[] receiveBuffer = new byte[1024];
                packet.setData(receiveBuffer);
                socket.receive(packet);
                String s = new String(receiveBuffer, 0, packet.getLength());
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(socket!=null)
                    socket.close();
            }
           return null;
}
}

class UppercaseUDPServer {

        public void launchToUppercaseServer(int port) {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(port);
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf,buf.length);

                int cpt=0;
                while (true) {
                    socket.receive(packet);
                    cpt++;
                    buf=new
String(buf,0,packet.getLength()).toUpperCase().getBytes();
                    packet.setData(buf,0,buf.length);
                    socket.send(packet);
                    if(cpt>0){
                        return;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(socket!=null)
                    socket.close();
            }
        }
    }


     public static void main(String[] args) {
            Problem p = new Problem();
            final Problem.UppercaseUDPServer serv= p.new
UppercaseUDPServer();
            Problem.UppercaseUDPClient cli = p.new UppercaseUDPClient();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    serv.launchToUppercaseServer(1666);
                }
            }).start();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE,
null, ex);
        }
         String s=cli.getEcho("localhost", 1666, "Rock it");
         if (s.compareTo("ROCK IT") == 0) {
                System.out.println("PROBLEM_SOLVED");
            } else {
                System.out.println("PROBLEM_FAILED");
            }

        }

}"""

c_code = """#include <stdio.h>
#include<stdlib.h>
#include <unistd.h>
#define N 20
int main(int argc , char* argv[])
{
     int a;
     for (int i = 0 ; i < atoi(argv[1]) ; ++i){
         printf("Hello World from Docker, it rocks!, i'm the %d iteration\\n" , i);
         //sleep(1);
        }
      char buff[N];
      char* s = fgets(buff, N , stdin);
      if (s != NULL)
         printf("la chaine entree est : %s\\n" , buff);
      else
        printf("failed\\n");
      return 0;
}
"""

python_code = """import sys
i = int(sys.argv[1])
for j in range(0 , i) :
    print "Hello world from Docker , it rocks!, i'm the "  + str(j) + " iteration"
line = sys.stdin.readlines();
print "the string is %s" %line
""" 
java_code = """import java.io.IOException;
public class Test {
public static void main(String[] args) throws IOException
  {
      for (int i = 0 ; i < Integer.parseInt(args[0]) ; i++)
          System.out.println("Hello world from Docker , it rocks!, i'm the "  + i + " iteration");
      for (int i = 0 ; i < 20 ; i++)
          System.out.print((char)System.in.read());
  }
}
"""
def check_connect_query (connectQuery) :
    print connectQuery
    print connectQuery['retCode']['description']
    print "session key obtained " + connectQuery['sessionKey']
    if connectQuery['retCode']['code'] != 0 :
        print 'something went wrong'
        sys.exit(1)
    return connectQuery['sessionKey']


def check_send_request_query (query) :
    print query['retCode']['description']
    print query['reference']
    if query['retCode']['code'] != 0 :
        print 'something went wrong'
        sys.exit(1)
    return query['reference']

def check_get_status_query (query) :
    print query['retCode']['description']
    print query['result']['description']
    print query['status']['description']
    if query['retCode']['code'] != 0 :
        print 'something went wrong'
        sys.exit(1)

def check_get_details_query (query) :
    check_get_status_query(query)
    stderr = getDetailsQuery['stdErr']
    stdout = getDetailsQuery['stdOut']
    time = getDetailsQuery['timeInfo']
    print 'Query information'
    print 'stdErr ==> %s' %stderr
    print 'stdOut ==> %s' %stdout
    print 'time ==> %s' %time
    return (stdout , stderr , time)
    
def check_disconnect_run_query (query) :
    print query['code']
    print query['description']


#logging.basicConfig(level=logging.INFO)
url = 'http://localhost:8888/ws/compileplatform?wsdl'
proxy = Client(url)
#logging.getLogger('suds.client').setLevel(logging.DEBUG)
print "Web methods supported by the platform"
print proxy

print "Retrieving supported languages..."
get_language_time = time.time()
langQuery = proxy.service.getLanguages()
get_language_time = time.time() - get_language_time
langMap = {}
if langQuery['retCode']['code'] == 0 :
    for lang in langQuery['languages'] :
        langMap[lang['langName']] = lang['idLang']
print "Supported languages : " + str(langMap)

print "Connecting on the platform...."
connect_time = time.time()
request = proxy.service.connect()
connect_time = time.time() - connect_time
sessionKey = check_connect_query(request)
print "========================================================================="
print "Test 1 : Sending the java client-server code on the platform...."
sendRequestQuery = proxy.service.sendRequest(sessionKey , langMap['java'],
                                                   client_serveur , "" , "" , "" , True)

task_reference = check_send_request_query(sendRequestQuery)

print "Getting status about task : " + str(task_reference)
sendGetStatusQuery = proxy.service.getStatus(sessionKey , task_reference)
check_get_status_query(sendGetStatusQuery)

print "Getting details about task : " + str(task_reference)
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test1) = check_get_details_query(getDetailsQuery)

print "============================================================================"
print "Test 2 : Sending the C source code on the platform....."
srt = time.time() * 1000
sendRequestQuery = proxy.service.sendRequest(sessionKey , langMap['c'] ,
                                                   c_code , "-std=c99" , "20" , "plateforme de compilation" , False)
srt = (time.time() * 1000) - srt
task_reference = check_send_request_query(sendRequestQuery)
comp_time1 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo']
tr_time1 = srt - comp_time1 

proxy.service.run(sessionKey , task_reference , "20" , "plateforme de compilation")
run_time1 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo'] - comp_time1

print "Getting status about task : " + str(task_reference)
sendGetStatusQuery = proxy.service.getStatus(sessionKey , task_reference)
check_get_status_query(sendGetStatusQuery)

print "Getting details about task : " + str(task_reference)
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test2) = check_get_details_query(getDetailsQuery)

print "=============================================================================="
print "Test 3 : Sending the Python source code on the platform....."
srt = time.time() * 1000
sendRequestQuery = proxy.service.sendRequest(sessionKey , langMap['python'] ,
                                                   python_code , "" , "20" , "plateforme de compilation" , False)
srt = (time.time() * 1000) - srt
task_reference = check_send_request_query(sendRequestQuery)

comp_time2 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo']
tr_time2 = srt - comp_time2

proxy.service.run(sessionKey , task_reference , "20" , "plateforme de compilation")
run_time2 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo']

print "Getting status about task : " + str(task_reference)
sendGetStatusQuery = proxy.service.getStatus(sessionKey , task_reference)
check_get_status_query(sendGetStatusQuery)

print "Getting details about task : " + str(task_reference)
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test3) = check_get_details_query(getDetailsQuery)

print "Running last task with new parameter...."
runQuery = proxy.service.run(sessionKey , task_reference , "10" , "plateforme d'execution")
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test3_bis) = check_get_details_query(getDetailsQuery)

print "================================================================================"
print "Test 4 : Sending the C++ source code on the platform....."
srt = time.time() * 1000
sendRequestQuery = proxy.service.sendRequest(sessionKey , langMap['c++'] ,
                                                   c_code , "-std=c99" , "20" , "plateforme de compilation" , False)
srt = (time.time() * 1000) - srt
task_reference = check_send_request_query(sendRequestQuery)
comp_time3 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo']
tr_time3 = srt - comp_time3

proxy.service.run(sessionKey , task_reference , "20" , "plateforme de compilation")
run_time3 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo'] - comp_time3

print "Getting status about task : " + str(task_reference)
sendGetStatusQuery = proxy.service.getStatus(sessionKey , task_reference)
check_get_status_query(sendGetStatusQuery)

print "Getting details about task : " + str(task_reference)
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test4) = check_get_details_query(getDetailsQuery)
print "=============================================================================="
print "Test 5 : Sending the Java source code on the platform....."
srt = time.time() * 1000
sendRequestQuery = proxy.service.sendRequest(sessionKey , langMap['java'] ,
                                                   java_code , "" , "20" , "plateforme de compilation" , False)
srt = (time.time() * 1000) - srt
task_reference = check_send_request_query(sendRequestQuery)
comp_time4 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo']
tr_time4 = srt - comp_time4

proxy.service.run(sessionKey , task_reference , "20" , "plateforme de compilation")
run_time4 = proxy.service.getDetails(sessionKey , task_reference)['timeInfo'] - comp_time4

print "Getting status about task : " + str(task_reference)
sendGetStatusQuery = proxy.service.getStatus(sessionKey , task_reference)
check_get_status_query(sendGetStatusQuery)

print "Getting details about task : " + str(task_reference)
getDetailsQuery = proxy.service.getDetails(sessionKey , task_reference)
(stdout , stderr , time_test5) = check_get_details_query(getDetailsQuery)

print "=============================================================================="
print "PERFORMANCE"
print "client-server in Java =======> %d " %time_test1
print ""
print "test in C             =======> transfert time : %d  compile time : %d  run time: %d  " %(tr_time1 , comp_time1 , run_time1)
print "same test in Python   =======> transfert time : %d  compile time : %d  run time: %d  " %(tr_time2 , comp_time2 , run_time2)
print "same test in C++      =======> transfert time : %d  compile time : %d  run time: %d  " %(tr_time3 , comp_time3 , run_time3)
print "same test in Java     =======> transfert time : %d  compile time : %d  run time: %d  " %(tr_time4 , comp_time4 , run_time4)
print ""
print "transfert time average ======> %f" %((tr_time1 + tr_time2 + tr_time3 + tr_time4) / 4)
print ""
print "last python test run  =======> task completion time : %d " %time_test3_bis
print "=============================================================================="

print "Disconnecting from the platform..."

disconnectQuery = proxy.service.disconnect(sessionKey)
check_disconnect_run_query(disconnectQuery)

print "End of the test......"
