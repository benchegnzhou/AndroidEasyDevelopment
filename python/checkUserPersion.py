'''
用户权限检测
'''
import settings
import operator


class CheckPersion():

    # 执行用户权限检测,检测失败将会推出程序，用户信息文件存储在本地
    def checkUserPersion(self):
        print('\n' * 30)
        print('----------------欢迎使用APP一键发布脚本请输入用户名和密码----------')
        print('\n' * 6)

        userLists = []
        try:
            # userFile = open(,'r',encoding='UTF-8')
            with open(settings.USER_INFO_PATH, 'r', encoding='UTF-8') as f:
                userLists = f.readlines()
                '''
                for line in userLists:
                    print(line)
                '''
        finally:
            print('\n' * 3)
        userName = input("Please input your userName: ")
        pwd = input("Please input your password: ")
        hasPersition = False
        try:
            for line in userLists:
                # 去掉换行符
                line = line.strip('\n')
                uName = line.split("=")[0]
                uPwd = line.split("=")[1]
                if (operator.eq(userName, uName) and operator.eq(pwd, uPwd)):
                    print("登录成功")
                    hasPersition = True
                # file_name(os.getcwd())
        finally:
            print('\n' * 3)
            if hasPersition == False:
                print("用户不存在或用户名密码错误，系统将推出....")
                sys.exit()


persionCheck = CheckPersion()
