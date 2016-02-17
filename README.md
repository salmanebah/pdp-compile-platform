# pdp-compile-platform
A secure and remote compilation and execution soap webservice using docker for the sandbox.

## Compiling
The project uses maven, from ```compilePlatform``` directory:
```sh
mvn clean compile
```
## Building the image
The server requires a docker image in order to run compilation and/or execution requests.

One can either pull an image with some compilers installed:

```docker pull salmanebah/pdp-compile-platform-img```

Or build a docker image from the ```Dockerfile```:

```docker build -t pdp-compile-platform-img . ```

## Running the tests
from ```compilePlatform```:
```sh
mvn test
```
## Starting the server
from ```compilePlatform```:
```sh
mvn exec:java -Dexec.mainClass="pdp.compileplatform.frontend.EntryPoint"
```
The server handles requests from port ```8888```.

### Available options
```sh
--dockerImag <image> : set Docker image to use for requests (default : salmanebah/pdp-compile-platform-img)
--dockerExecTime <time in sec> : set Docker execution time per request (default : 4.0)
--dockerMemLimit <limit>[<b|m|g>] : set Docker memory limit per request [optinal b,m,g] (default : 128m)
--sessionCleanerTime <time in min> : set time to clean sessions (default : 15)
--sessionMaxInactivity <time in min> : set maximum time of session inactivity (default : 1)
--taskMaxInactivity <time in min> : set maximum time of task inactivity (default : 1)
--threadsNumber <n> : number of simultaneous connections to handle (default : 10)
--help : show this help
```
For example, to use the ```threadsNumber``` option:

```mvn exec:java -Dexec.mainClass="pdp.compileplatform.frontend.EntryPoint" -Dexec.args="--threadsNumber 4" ```

**NOTE:** the option ```--dockerMemLimit``` requires the following:

1. Change ```/etc/default/grub``` by adding:
```GRUB_CMDLINE_LINUX="cgroup_enable=memory swapaccount=1```

2. Update grub: ```update-grub```
3. Restart for the change to take effect.

## A client example with python and the suds library
```python
#! /usr/bin/env python

import sys
from suds.client import Client

CODE = """import sys
i = int(sys.argv[1])
for j in range(0 , i) :
    print "Hello world from Docker , it rocks!, i'm the "  + str(j) + " iteration"
line = sys.stdin.readlines();
print "the string is %s" %line
"""

URL = 'http://localhost:8888/ws/compileplatform?wsdl'
PROXY = Client(URL)
print "Web methods supported by the platform"
print PROXY
# get  the available languages
LANGQUERY = PROXY.service.getLanguages()
# build a map of available languages
LANGMAP = {}
if LANGQUERY['retCode']['code'] == 0:
    for lang in LANGQUERY['languages']:
        LANGMAP[lang['langName']] = lang['idLang']

print "Supported languages : " + str(LANGMAP)
# connecting to the platform
REQUEST = PROXY.service.connect()
# check if success
if REQUEST['retCode']['code'] != 0:
    print 'something went wrong'
    sys.exit(1)
# get the session key for requests
SESSION_KEY = REQUEST['sessionKey']
# submit an execution request
TASK_REQUEST = PROXY.service.sendRequest(SESSION_KEY, LANGMAP['python'],
                                         CODE, "",
                                         "20",
                                         "compilation platform",
                                         False)
if TASK_REQUEST['retCode']['code'] != 0:
    print 'something went wrong'
    sys.exit(1)
# get the task reference
TASK_ID = TASK_REQUEST['reference']

# run the task
PROXY.service.run(SESSION_KEY, TASK_ID, "20", "compilation platform")
# get details
DETAILS_REQUEST = PROXY.service.getDetails(SESSION_KEY, TASK_ID)
# print execution details
print 'Result: ' + str(DETAILS_REQUEST['stdOut'])
print 'Time: ' + str(DETAILS_REQUEST['timeInfo'])

# disconnecting
DISCONNECT_REQUEST = PROXY.service.disconnect(SESSION_KEY)
print 'Disconnected from the platform with code: ' \
      + str(DISCONNECT_REQUEST['code'])
```

Other examples can be found in ```client_test```
