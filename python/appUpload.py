# coding=utf-8
import json
import operator
import os
import sqlite3
import sys
import time

import requests

# path1=os.path.abspath('.')#获取当前脚本所在的路径
# path2=os.path.abspath('..')#获取当前脚本所在路径的上一级路径

# 获取当前文件路径				G:\house\app\android\python\upload_file_property.py
current_path = os.path.abspath(__file__)
# 获取当前文件的父目录 			G:\house\app\android\python
father_path = os.path.abspath(os.path.dirname(current_path) + os.path.sep + ".")
# 获取当前文件的父目录的父目录 	G:\house\app\android
root_path = os.path.abspath(os.path.dirname(father_path) + os.path.sep + ".")

# 文件上传
# urlRealse = 'http://www.correctmap.com.cn/pub-service/Service?service=file&function=upload'
urlRealse = 'http://119.3.162.131:7080/pub-service/Service?service=file&function=upload'

# 版本设置
urlUpMsgRealse = 'http://www.correctmap.com.cn/pub-service/Service?service=dataManager&function=newAppVerson'

_api_key = "c8364150e5514d79cf7563013e8b231d"
uKey = "29ee634c5e80425b93c30667c168a11b"

dpPath = father_path + '/log/updataLog.db'
operation = ''
url = ''
userName = "zbc"

'''
try:

	#os.system("../src/build.gradle")
	#os.system("cd "+root_path+"/src")
	#os.system("dir")
	#os.system("gradle assemble")


	#pyPath=('python %s/src/dabao.py'%root_path)
	#print ("lujign -----%s" % pyPath)
	#os.system(pyPath)

except:
	print ('---------无法正常打包-----------')
	#sys.exit()
finally:
	print ('\n'*2)

'''

''' 
pyPath=('gradle %s/src/assemble'%root_path)
print ("lujign -----%s" % pyPath)
os.system(pyPath) 
'''

# 读取版本信息
try:
    print("\n" * 3)
    print("***************************************************************************************\n")
    print("*                              读取配置文件           ")
    print("\n*" * 2)
    print("*   配置文件路径 " + "%s\\versionMsg.properties\n" % (root_path))
    with open('%s\\versionMsg.properties' % (root_path), 'r', encoding='UTF-8') as f:
        # print("--------file_object path"+root_path+'/src/versionMsg.properties')
        msg = f.read()
        msg = msg.replace('\n', "\\n")
        print("*   配置信息" + msg)
        print("*\n" * 3)
        print("***************************************************************************************\n")
        operation = json.loads(msg)
finally:
    print('\n' * 3)


# 执行用户权限检测,检测失败将会推出程序，用户信息文件存储在本地
def checkUserPersion():
    print('\n' * 30)
    print('----------------欢迎使用APP一键发布脚本请输入用户名和密码----------')
    print('\n' * 6)

    userLists = []
    try:
        # userFile = open(,'r',encoding='UTF-8')
        with open('%s//userInform.config' % (father_path), 'r', encoding='UTF-8') as f:
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

    # 递归遍历所有的apk文件
    # def listFile(apkDir):
    #     dirList = os.listdir()


# 所有apk集合
apkList = set()


# 扫描文件根目录，获取可用apk文件选择指定文件上传
def scannerLocalApk(rootFile):
    print('\n' * 4)
    print("************************************************************************")
    print("*                                    正在扫描apk文件\n")
    print('*\n' * 2)
    print('*            遍历当前文件得到如下结果')
    print('\n' * 2)

    for root, dirs, files in os.walk(rootFile):
        # root 表示当前正在访问的文件夹路径
        # dirs 表示该文件夹下的子目录名list
        # files 表示该文件夹下的文件list

        # 遍历文件
        for f in files:
            currentFile = os.path.join(root, f)
            if currentFile.endswith(".apk") and os.path.isfile(currentFile):
                apkList.add(currentFile)
                print("*" + currentFile + '\n')

        # 遍历所有的文件夹
        for d in dirs:
            print("* 源文件" + os.path.join(root, d) + '\n')

    print('*\n' * 4)


# 获取到release包
def getApk(apkLists, apkName):
    # 不存在，返回null
    if len(apkLists) == 0:
        return ""
    for every in iter(apkLists):
        # print(every.find(apkName))
        print(apkName in every)
        if every.endswith("_release.apk") and apkName in every:
            print("apkLists__" + every)
            return every


# 写入本地日志文件，包含文本和数据库两部分
def writeLocalLog(appPort, appSize, up2ZtscSuccess, up2pugongyingSuccess):
    print("up2ZtscSuccess == %s \n" % (up2ZtscSuccess))
    print("up2pugongyingSuccess == %s \n" % (up2pugongyingSuccess))
    apkTypeName = ""
    print("******************************************************")
    print("\n*            正在生成日志文件      ")
    print("\n*" * 2)
    # 日志文件写入 operation['appPort']
    if 'publicApp' == appPort:
        apkTypeName = '公众版'
    # writeLocalLog(banben)
    elif 'propertyAPP' == appPort:
        apkTypeName = '物业版'
        # writeLocalLog(banben)
    elif 'committeeAPP' == appPort:
        apkTypeName = '业委会版'
        # writeLocalLog(banben)
    elif 'b2cSallerLiteAPP' == appPort:
        apkTypeName = '商城简版'
        # writeLocalLog(banben)

    # 文件操作日志写入
    userLists = []
    logFile = os.path.abspath(os.path.dirname(dpPath) + os.path.sep + ".")
    print("日志文件根目录" + logFile)
    # 判断路径是否存在
    # 存在     True
    # 不存在   False
    if not os.path.exists(logFile):
        # 多层创建目录
        os.makedirs(logFile)

    with open(logFile + '/updataLog.log', 'a', encoding='UTF-8') as faa:
        faa.writelines("\n")
        faa.writelines('操作时间：' + time.strftime("%Y.%m.%d %H:%I:%S", time.localtime(time.time())))
        faa.writelines("\n")
        faa.writelines(userName + '  上传了 ' + apkTypeName + ' App')
        faa.writelines("\n")
        faa.writelines("版本名称：" + operation['newVersionName'])
        faa.writelines("\n")
        faa.writelines("版本编号：" + operation['newVersionCode'])
        faa.writelines("\n")
        faa.writelines("安装包大小：%.2f KB" % (appSize / 1024.0))
        faa.writelines("\n")
        faa.writelines("上传公司内部服务器： %s " % ("成功上传" if up2ZtscSuccess else "上传失败"))
        faa.writelines("\n")
        faa.writelines("上传蒲公英： %s" % ("成功上传" if up2pugongyingSuccess else "上传失败"))
        faa.writelines("\n")
        faa.writelines('操作人：' + userName)
        faa.writelines("\n" * 10)
    # 格式化时间戳为标准格式
    # print (time.strftime('%Y.%m.%d',time.localtime(time.time())))

    # 创建数据库	参考地址：https://www.cnblogs.com/bigming/p/7228044.html

    '''
    values = {'newVersionName' : operation['newVersionName'],
              'newVersionCode' :  operation['newVersionCode'],
              'updateMsg' :  operation['updateMsg'],
              'isPad' :  operation['isPad'], 
              'updateNecess' : operation['updateNecess'], 
              'appPort' : operation['appPort'],
              'appSize' : size,
              'resourceId' : fileId} 
              '''
    try:
        conn = sqlite3.connect(dpPath)
        cur = conn.cursor()
        create_sql = """create table updataLog(int integer,operatorName text,operatorTime text,operation text,appPort text,newVersionName text,newVersionCode text,updateNecess text,updateMsg text,isPad text,appSize text,uptoZtscSuccess text,uptoPgySuccess text);"""
        cur.execute(create_sql)
        cur.close()
        conn.close()
    except:
        # print ('process exception')
        print('\n*' * 2)
    finally:
        print("* 操作时间:" + time.strftime("%Y.%m.%d %H:%I:%S", time.localtime(time.time())))

    operatorTime = time.strftime("%Y.%m.%d %H:%I:%S", time.localtime(time.time()))
    appPort = operation['appPort']
    newVersionName = operation['newVersionName']
    newVersionCode = operation['newVersionCode']
    updateNecess = operation['updateNecess']
    updateMsg = operation['updateMsg']
    updateMsg = updateMsg.replace('\n', "\\n");
    isPad = '兼容' if operator.eq(operation['isPad'], '1') else '不兼容'

    appSize = "%.2f KB" % (appSize / 1024.0)

    # 向数据库插入数据
    conn = sqlite3.connect(dpPath)
    cur = conn.cursor()
    # insert_sql="""insert into updataLog values(1,userName,operatorTime,'发布新版本',appPort,newVersionName,newVersionCode,updateNecess,updateMsg,isPad,appSize);"""
    cur.execute('insert into updataLog values(?,?,?,?,?,?,?,?,?,?,?,?,?)', (
        1, userName, operatorTime, '发布新版本', appPort, newVersionName, newVersionCode, updateNecess, updateMsg, isPad,
        appSize, "成功" if up2ZtscSuccess else "失败", "成功" if up2pugongyingSuccess else "失败"))
    conn.commit()
    cur.close()
    conn.close()


def writeText(context):
    with open(father_path + '/data.log', 'a', encoding='UTF-8') as faa:
        faa.writelines("\n")
        faa.writelines("\n")
        faa.writelines(context)


# 将这个APP上传蒲公英
def upAppToPugognying(push2ZTSC, appPath, uKey, _api_key, updateDescription):
    try:
        if push2ZTSC != 'true':
            return False

        # (必填)应用安装方式，值为(2,3)。2：密码安装，3：邀请安装
        buildInstallType = 1
        buildPassword = '123456'
        murl = 'https://www.pgyer.com/apiv2/app/upload'
        print("\n" * 3)
        print("***************************************************************************************\n")
        print("*                              APP上传蒲公英             ")
        print("\n*" * 2)
        print("*   待上传apk文件" + appPath)
        print("\n*" * 2)

        data = {
            'uKey': uKey,
            '_api_key': _api_key,
            'buildInstallType': buildInstallType,
            #'buildPassword': buildPassword,
            'buildUpdateDescription': updateDescription
        }

        files = {'file': open(appPath,
                              'rb')}

        response = requests.post(murl, data=data, files=files)
        # print(response.text)
        writeText(response.text)
        res = json.loads(response.text)

        print(res)
        if res['code'] == 0:
            print('\n*' * 2)
            print('*****************************apk上传蒲公英成功打印日志如下****************************')
            print('\n*' * 2)
        else:
            print('\n*' * 2)
            print('*****************************上传失败****************************')
            print('\n*' * 2)
        return res['code'] == 0

    except Exception as err:
        print('\n*' * 2)
        print('********************************上传失败******************************')
        print('\n*' * 2)
        print(err)
        return False


'''
		if "." in filename:
		#以“.”为分割点获取文件名
		filename=filename.split(".")[0]    
		fi_name.append(filename)
		print fi_name
'''

'''
[i for i in os.listdir('.') if os.path.isfile(i) and os.path.splitext(i)[1]=='.py']
'''

scannerLocalApk(root_path + '\\app\\build\\outputs\\apk')
apkPath = getApk(apkList, operation['newVersionName'])

# 检测是否存在
if len(apkPath) == 0:
    print(apkPath)
    raise RuntimeError("apk文件不存在")  # 提示异常，主动抛出异常


# 上传APP到正图内部服务器
def upLoadAppZtsc(push2ZTSC, uploadApp):
    try:
        if push2ZTSC != 'true':
            return False

        files = {'file': open(uploadApp, 'rb')}

        size = os.path.getsize(uploadApp)

        print("\n*********************************************************************")
        print("\n**" * 2)
        print("\n********       文件大小 %.2f KB                                       " % (size / 1024.0))
        print("\n**" * 2)
        print("\n**" * 2)
        print("\n********       apk上传的地址:%s...  " % urlRealse)
        print("\n**" * 2)
        print("\n**" * 2)
        print("\n********       Apk文件正在上传请稍后...                             ")
        print("\n**" * 2)
        print("\n**" * 2)
        print("\n********       文件传中请在稍后...                                 ")
        print("\n**" * 2)

        r = requests.post(urlRealse, files=files)
        print(r.url)
        print(r.text)

        res = json.loads(r.text)
        print(res['result']['resourceId'])

        fileId = res['result']['resourceId']

        # 检查apk文件是否上传成功

        if (res['code'] == 200) and operator.eq('0', res['result']['status']):
            print('\n')
        else:
            print('*             apk文件上传失败               *')
            print('\n')
            aa = input("Please input any key exit: ")
            sys.exit()

        '''
        values = {'newVersion' : '1.0.5',  
                  'newVersionCode' : '5',  
                  'updateMsg' : '1.全新关注关系，快来关注你感兴趣的人吧\n2.赛事直播间增加助威功能，为你喜欢的战队打CALL吧\n3.认证体系升级。新增全新认证标识并优化个人主页\n4.优化用户体验，修复已知问题',
                  'isPad' : '1', 
                  'updateNecess' : 'must', 
                  'appPort' : 'publicApp',  
                  'appSize' : size,  
                  'resourceId' : fileId} 
        '''

        values = {'newVersion': operation['newVersionName'],
                  'newVersionCode': operation['newVersionCode'],
                  'updateMsg': operation['updateMsg'],
                  'isPad': operation['isPad'],
                  'updateNecess': operation['updateNecess'],
                  'appPort': operation['appPort'],
                  'appSize': size,
                  'resourceId': fileId}

        print('\n' * 4)
        print('------------文件上传成功请确认版本信息(同意请录入Y , 不同意请录入 N)------------')
        print('\n' * 2)

        print('   newVersionName : %s\n' % values['newVersion'])
        print('   newVersionCode : %s\n' % values['newVersionCode'])
        print('   updateMsg : \n\n%s\n' % values['updateMsg'])
        print('   isPad : %s\n' % values['isPad'])
        print('   updateNecess : %s\n' % values['updateNecess'])
        print('   appSize : %s B\n' % values['appSize'])

        print('\n' * 4)

        # if operator.eq('Y', result) == False:
        #     sys.exit()
        #     print('程序已退出，请修改版本信息后重新启动程序')

        response = requests.post(urlUpMsgRealse, data=values)
        print(response.text)

        res = json.loads(response.text)
        print(res['result']['status'])
        print('\n' * 6)
        # print("---------------------Apk文件: %s-----------------------" % res['result']['information'])
        print("*\n*\n***                      apk上传公司内部服务器成功       ")
        print('\n' * 6)
        print("\n*********************************************************************")
        print('\n' * 12)
        # {"code":200,"message":"","result":{"information":"上传成功","status":"0"}}
        return res['code'] == 200 and operator.eq('0', res['result']['status'])
    except Exception as err:
        print(err)
        print("\n*********************************************************************")
        return False


# upToZtscSuccess = True
upToZtscSuccess = upLoadAppZtsc(operation['push2ZTSC'], apkPath)
upPugongyingSuccess = upAppToPugognying(operation['push2Pugongying'], apkPath, uKey, _api_key, operation['updateMsg'])

# 日志文件写入
writeLocalLog(operation['appPort'], os.path.getsize(apkPath), upToZtscSuccess, upPugongyingSuccess)

print('\n' * 4)
# result = input("Please enter your confirmation result (Y , N ): ")
result = input(" apk上传完成，最新版本 " + operation['newVersionName'] + "   Please input any key exit:  ")
print('\n' * 2)
