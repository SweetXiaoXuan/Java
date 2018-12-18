修改分支名称
git branch -m 原名 新

添加分支
git branch [branch name]

git status  查看状态：被修改、添加、删除的文件；其中「On branch master」会说明当前在哪个分支

git branch   查看所有分支，前面带「*」是当前所在分支

git remote  查看所有的远程库

git remote -v 查看所有的远程库地址

git add .  添加所有被修改、添加、删除的文件

git add <filename>  添加指定被修改、添加、删除的文件

git commit -m "<content>"   添加提交说明

git pull <remote name> <branch name>  从远程库更新代码到本地

git push <remote name> <branch name>  将本地提交的推送到远程库
