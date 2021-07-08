# JIraHelper

### introduction
this tool is used to generate the release report for Bamboo and Mooncake

### before using
- should git clone all the Bamboo's and Mooncake's services in the same folder
- should correct the `project_dir` of `sprint-release-report.sh` with above folder location
such as:
````
    project_dir="/Users/i340818/workspace/github"
````
- add your sap sso basic auth base64 as system env variables (local_authorization)
  1. cd to your home folder.
  2. open file .profile
  3. add your basic auth text as env var, such as
  4. run `source .profile`
```
  export local_authorization='Basic d2lsbC5zdW5Ac2FwLmNvbToxMjM0NTY3ODkw' // mailbox address:password  
``` 
  

### how to use
- open this project, and then run
- visit http://localhost:9527/
- click 'Mooncake\'s release report or 'Bamboo\'s release report' buttton
- wait for a while to get the release report

### command help
- --help, -h: print the command help information
- --all, -a: generate both Bamboo's and Mooncake's services release report [default]
- --bamboo, -b: only generate Bamboo's services release report
- --mooncake, -m: only generate Mooncake's services release report

### generate files introduction
- release_notes.txt: pr report based on develop and master diff of github
- mooncake_release_report.md: release report for Mooncake's services
- bamboo_release_report.md: release report for Bamboo's services
