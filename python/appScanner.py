'''
扫描硬盘下的APP文件及其目录
'''
import loggerLocal as log
import os
import baseResponse as bResp
import setting


class Scanner():
    def __init__(self):
        self.apkList = []
        self.selectApkPath = ""
        self.appUpType = ""

    # 扫描文件根目录，获取可用apk文件选择指定文件上传
    def scannerLocalApk(self, rootFile):
        result = bResp.taskRespose()
        result.markResultSuccess("成功")

        try:
            print('\n' * 4)
            log.loggerP.printMsgOneLineCenterCommon("")
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("扫描磁盘获取APK安装文件")
            print("*\n" * 2, end="")
            print("* 正在扫描apk文件")
            print('*\n' * 2, end="")
            print('* 遍历当前文件得到如下结果')
            print('*\n' * 2, end="")

            for root, dirs, files in os.walk(rootFile):
                # root 表示当前正在访问的文件夹路径
                # dirs 表示该文件夹下的子目录名list
                # files 表示该文件夹下的文件list

                # 遍历文件
                for f in files:
                    currentFile = os.path.join(root, f)
                    if currentFile.endswith(".apk") and os.path.isfile(currentFile):
                        self.apkList.append(currentFile)
                        # print("* %s. %s" % (len(apkList), currentFile))

                # 遍历所有的文件夹
                for d in dirs:
                    print("* 源文件" + os.path.join(root, d))

            print('*\n' * 4, end="")
            print("共找到 %s 个APK文件，打印如下" % len(self.apkList))
            num = 1
            for file in self.apkList:
                print("* %s. %s" % (num, file))
                num += 1
            print('*\n' * 4, end="")

        except Exception as e:
            print(e)
            result.markResultFail("遍历APK文件失败")
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("文件遍历失败")
        finally:
            log.loggerP.printMsgOneLineCenterCommon("")
            return result, self.apkList

    def getApk(self, apkName):
        '''
        获取到release包
        :param apkLists:
        :param apkName:
        :return:
        '''
        print('\n' * 4)
        result = bResp.taskRespose()
        result.markResultSuccess("成功")
        log.loggerP.printMsgOneLineCenterCommon("")
        log.loggerP.printMsgOneLineCenterCommonCustomSignal("正在抓取版本号为 %s 打包类型为 %s 的APK安装包文件"  %(apkName,self.appUpType))
        try:

            # 不存在，返回null
            if len(self.apkList) > 0:
                for every in self.apkList:
                    # print(every.find(apkName))
                    # print(apkName in every)
                    if every.endswith("_%s.apk" % self.appUpType) and apkName in every:
                        print("*\n" * 3, end="")
                        print("* 获取到需要上传的APK文件")
                        print("* " + every)
                        self.selectApkPath = every
                        break
        except Exception as e:
            print(e)
            print("*\n" * 3, end="")
            result.markResultFail("版本号为 %s 打包类型为 %s 的APK安装包抓取失败" %(apkName,self.appUpType) )
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("版本号为 %s 打包类型为 %s 的APK安装包抓取失败" %(apkName,self.appUpType))
        finally:
            if result.code == bResp.TASK_RESULT_CODE.RESULT_OK:
                if len(self.selectApkPath) == 0:
                    print("*\n" * 3, end="")
                    result.markResultFail("版本号为 %s 打包类型为 %s 的APK安装包抓取失败,不存在目标版本的 %s 安装包"  %(apkName,self.appUpType,self.appUpType))
                    log.loggerP.printMsgOneLineCenterCommonCustomSignal("文件遍历失败")

            log.loggerP.printMsgOneLineCenterCommon("")
            return result, self.selectApkPath

    def scannerSelectApk(self, scannerDir, appVersionName, appUpType):
        self.appUpType = appUpType
        A, a = self.scannerLocalApk(setting.AKP_SCANNER_ROOT)
        if A.code == bResp.TASK_RESULT_CODE.RESULT_OK:
            return self.getApk(appVersionName)
        else:
            return A


if __name__ == '__main__':
    sc = Scanner()
    log.loggerP.loggerInit(50)
    print(setting.AKP_SCANNER_ROOT)
    A, a = sc.scannerLocalApk(setting.AKP_SCANNER_ROOT)
    sc.getApk("1.7.209")
