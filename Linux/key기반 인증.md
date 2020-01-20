## key기반 인증



centos : 계정 만들기 sshd-keygen -> ssh-keygen 

-> ubuntu(root) : 계정 만들기 

-> ubuntu에서 adduser student3(pw = student)  -> cat /etc/passwd (student3가 생성됐는지 확인)

-> centos에서 cd .ssh 가서 ssh-copy-id 계정이름(student3)@ubuntu ip (pw = student) 

-> centos에서 cat id_rsa.pub -> xshell(새로생성, student3)

-> student3에 접속, cd .ssh/ cat authorized_keys

-> centos에서 ssh student3@10.0.0.130 





