# JIraHelper

### introduction
this tool is used to generate the release report for Bamboo and Mooncake

### command help
- --help, -h: print the command help information
- --all, -a: generate both Bamboo's and Mooncake's services release report [default]
- --bamboo, -b: only generate Bamboo's services release report
- --mooncake, -m: only generate Mooncake's services release report

### generate files introduction
- release_notes.txt: pr report based on develop and master diff of github
- mooncake_release_report.md: release report for Mooncake's services
- bamboo_release_report.md: release report for Bamboo's services
