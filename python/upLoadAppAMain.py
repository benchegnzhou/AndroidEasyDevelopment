import loggerLocal
import appUp2pugongyign as upPGY
import setting
import appScanner
import baseResponse as bResp
import appUp2ZTSC as upZTSC
import readMsg
import os
import commonCore as common
import operationRecords as oRecords


class main():
    def __init__(self):
        self.apkPath = ""
        self.apkVersionName = ""
        self.updateDescription = ""
        self.values = []
        self.appPort = ""
        self.appPortName = ""
        self.appUpType = ""
        self.upDataMSgJson = ""
        self.appUp2ZCSCType = "release"
        self.appUp2PugongyingType = "release"
        self.pugongying_api_key = ""
        self.pugongying_uKey = ""
        self.up2ZtscApkUrl = "http://119.3.162.131:7080/pub-service/Service?service=file&function=upload"
        self.up2ZtscMsgUrl = "http://www.correctmap.com.cn/pub-service/Service?service=dataManager&function=newAppVerson"
        self.push2ZTSC = False
        self.push2PYG = False
        self.upZTSCSuccess = False
        self.upPGYSuccess = False

    def initLogger(self):
        loggerLocal.loggerP.loggerInit(180)

    def scannerApk(self):
        self.apkPath = ""
        A, self.apkPath = appScanner.Scanner().scannerSelectApk(self.apkPath, self.apkVersionName, self.appUpType)
        return A, self.apkPath

    def appUp2ZTSC(self):

        # result, msg = upZTSC.appUpload().upAppZtc(self.push2ZTSC, self.apkPath
        #                                           , setting.ZTSC_URL_REALSE, setting.ZTSC_UPDATA_MSG_URL,
        #                                           self.upDataMSgJson)

        result, msg = upZTSC.appUpload().upAppZtc(self.push2ZTSC, self.apkPath
                                                  , self.up2ZtscApkUrl, self.up2ZtscMsgUrl,
                                                  self.upDataMSgJson)

        if result.code == bResp.TASK_RESULT_CODE.RESULT_OK:
            self.upZTSCSuccess = True
        else:
            self.upZTSCSuccess = False

    def appUpPugongying(self):
        '''
        上传蒲公英
        :return:
        '''
        # result = upPGY.AppUpPGY().upAppToPugognying(self.push2PYG, self.apkPath, self.apkVersionName, setting.PGY_uKey,
        #                                             setting.PGY_api_key,
        #                                             self.updateDescription)

        result = upPGY.AppUpPGY().upAppToPugognying(self.push2PYG, self.apkPath, self.apkVersionName, self.pugongying_uKey,
                                                    self.pugongying_api_key,
                                                    self.updateDescription)
        if result.code == bResp.TASK_RESULT_CODE.RESULT_OK:
            self.upPGYSuccess = True
        else:
            self.upPGYSuccess = False
            print(result.msg)

    def readUpdataMsg(self):
        '''
        读取更新配置文件
        :return:
        '''
        result, json = readMsg.re.readUpMsg(common.root_path)
        if result.code == bResp.TASK_RESULT_CODE.RESULT_OK:
            self.upDataMSgJson = json
            self.apkVersionName = json["newVersionName"]
            self.push2ZTSC = 'true' == json['push2ZTSC'].lower()
            self.push2PYG = 'true' == json['push2Pugongying'].lower()

            self.appPort = json["appPort"]
            self.appPortName = json["appPortName"]
            self.appUpType = json["appUpType"]
            self.pugongying_api_key = json["pugongying_api_key"]
            self.pugongying_uKey = json["pugongying_uKey"]
            self.up2ZtscApkUrl = json["up2ZtscApkUrl"]
            self.up2ZtscMsgUrl = json["up2ZtscMsgUrl"]

            if len(self.appUpType) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置需要上传apk的appUpType")
            if len(self.pugongying_api_key) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置蒲公英账号的pugongying_api_key")
            if len(self.pugongying_uKey) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置蒲公英账号的pugongying_uKey")
            if len(self.up2ZtscApkUrl) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置公司服务的up2ZtscApkUrl")
            if len(self.pugongying_uKey) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置公司服务器的up2ZtscMsgUrl")
            if len(self.appPort) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置用于区别apk端的标识appPort")
            if len(self.appPortName) == 0:
                raise Exception("请在项目的commonConfig.gradle中配置apk端的名称appPortName")
        else:
            raise Exception("更新日志信息读取失败，程序将退出")

    def getSuccess(self, signal):
        if signal:
            return "成功"
        else:
            return "失败"

    def writeLog(self):

        oRecords.logHelper.writeLog(self.appPortName,self.appPortName, os.path.getsize(self.apkPath),
                                    self.upDataMSgJson['newVersionCode'],
                                    self.upDataMSgJson['newVersionName'], self.upDataMSgJson['isPad'],
                                    self.upDataMSgJson['updateNecess'], self.upDataMSgJson['updateMsg'], "zbc"
                                    , self.upZTSCSuccess, self.upPGYSuccess)


if __name__ == '__main__':

    try:
        m = main()
        m.initLogger()
        m.readUpdataMsg()
        A, appPath = m.scannerApk()
        if A.code == bResp.TASK_RESULT_CODE.RESULT_ERROR:
            raise Exception(A.msg)
        m.appUp2ZTSC()
        m.appUpPugongying()

        # 输出APP上传信息
        print("\n" * 4, end="")
        loggerLocal.loggerP.printMsgOneLineCenterCommon("*")
        loggerLocal.loggerP.printMsgOneLineCenterCommonCustomSignal("APP上传结果如下")
        print("*\n" * 4, end="")
        print("* APK安装包上传公司服务器是否成功      %s" % (m.getSuccess(m.upZTSCSuccess)))
        print("* APK安装包上传蒲公英服务器是否成功    %s" % (m.getSuccess(m.upPGYSuccess)))
        print("*\n" * 2, end="")
        loggerLocal.loggerP.printMsgOneLineCenterCommon("*")

        print("\n" * 4, end="")
        m.writeLog()
        print("\n" * 10, end="")

        loggerLocal.loggerP.printMsgOneLineCenterCommon("*")
        loggerLocal.loggerP.printMsgOneLineCenterCommonCustomSignal("上传任务完成欢迎下次使用")
        print("\n" * 4, end="")

    except Exception as e:
        print(e)
    finally:
        input("请输入任意键退出程序")
