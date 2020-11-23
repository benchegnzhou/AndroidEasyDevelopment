import requests
import json
import time
import sys
import threading
import os
import baseResponse as bResp
import loggerLocal as log
import ctypes
import operator


# 文件上传
# urlRealse = 'http://119.3.162.131:7080/pub-service/Service?service=file&function=upload'

# uploadApp = "G:\\house\\app\\work\\b2c-saller-app-lite-android\\app\\build\outputs\\apk\\baidu\\release\\b2c_saller_lite_v1.7.209_baidu_release.apk"


class appUpload:

    def __init__(self):
        self.total_size = 100
        self.recv_size = 0
        # app 本地地址
        self.uploadAppPath = ""
        # APP 上传服务器地址
        self.urlRealse = ""
        self.fileId = ""
        self.values = []

    def progress(self, percent, width=50):
        if percent > 1:  # 如果百分比大于1的话则取1
            percent = 1
        show_str = ('[%%-%ds]' % width) % (int(percent * width) * '#')
        # 一共50个#，%d 无符号整型数,-代表左对齐，不换行输出，两个% % 代表一个单纯的%，对应的是后面的s，后面为控制#号的个数
        # print(show_str)  #[###############               ] show_str ，每次都输出一次
        print("\r %s %s %%" % (show_str, int(percent * 100)), flush=True, end='')  # file=sys.stdout,
        # print("\r %s %%" % ( int(percent * 100)),flush=True, end='') #file=sys.stdout,
        # print(" %s" % percent, flush=True, end='')

        # print(pthread_level1())
        # \r 代表调到行首的意思，\n为换行的意思，fiel代表输出到哪，flush=True代表无延迟，立马刷新。第二个%s是百分比

    def my_callback(self, monitor):
        # Your callback function
        # print(monitor.bytes_read)
        percent = monitor.bytes_read / self.total_size
        self.progress(percent, width=50)  # 调用进度条函数，将百分比传进去

    # 上传APP到正图内部服务器
    def upLoadAppZtsc(self, push2ZTSC):
        result = bResp.taskRespose()
        result.markResultSuccess("成功")
        try:

            if not push2ZTSC:
                result.markResultFail("配置文件中关闭了上传内部服务器权限")
                return result
            print("*\n" * 4, end="")
            log.loggerP.printMsgOneLineCenterCommon("")
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("APK 文件上传正图服务器，完成内部更新")
            files = {'file': open(self.uploadAppPath, 'rb')}

            self.total_size = os.path.getsize(self.uploadAppPath)

            print("\n*" * 2, end="")
            print("\n* APK文件 %.2f MB" % (self.total_size / 1024.0 / 1024.0), end="")
            print("\n*", end="")
            print("\n* 服务器地址:%s...  " % self.urlRealse, end="")
            print("\n*", end="")
            print("\n* 文件传中请在稍后... ", end="")
            print("\n**" * 2, end="")
            r = requests.post(self.urlRealse, files=files)
            print('*\n' * 3)
            # print(r.url)
            print("* 服务器返回如下信息")
            print(r.text)

            res = json.loads(r.text)
            # print(res['result']['resourceId'])

            self.fileId = res['result']['resourceId']

            # 检查apk文件是否上传成功
            if (res['code'] == 200) and operator.eq('0', res['result']['status']):
                print("\n* 文件上传成功", end="")
            else:
                print("\n* 文件上传失败", end="")
                result.markResultFail("文件上传失败")


        except Exception as e:
            print(e)

        finally:
            return result, self.fileId

    def upDateAppMsg(self, urlUpMsgRealse):
        '''
        同步上传版本更新信息
        '''
        result = bResp.taskRespose()
        result.markResultSuccess("成功")
        log.loggerP.printMsgOneLineCenterCommon("")
        log.loggerP.printMsgOneLineCenterCommonCustomSignal("版本信息写入公司服务器")

        try:

            print("*\n" * 3, end="")
            print('--版本信息如下------------------------')
            print('newVersionName : %s\n' % self.values['newVersion'])
            print('newVersionCode : %s\n' % self.values['newVersionCode'])
            print('updateMsg : \n\n%s\n' % self.values['updateMsg'])
            print('isPad : %s\n' % self.values['isPad'])
            print('updateNecess : %s\n' % self.values['updateNecess'])
            print('appSize : %s MB\n' % (self.values['appSize'] / 1024 / 1024))
            print("*\n" * 3, end="")
            response = requests.post(urlUpMsgRealse, data=self.values)
            print("*服务器返回信息")
            print(response.text)

            res = json.loads(response.text)

            print('*\n' * 3)
            if res['code'] == 200 and operator.eq('0', res['result']['status']):
                result.markResultSuccess("成功")
            else:
                result.markResultFail("版本信息写入服务器失败")
                print(res['result']['status'])
        except Exception as e:
            result.markResultFail("版本信息写入服务器失败")
            print(e)
            pass
        finally:
            log.loggerP.printMsgOneLineCenterCommon("")
            return result, ""

    def getMsg(self, updataMsgJson):
        self.values = {'newVersion': updataMsgJson['newVersionName'],
                       'newVersionCode': updataMsgJson['newVersionCode'],
                       'updateMsg': updataMsgJson['updateMsg'],
                       'isPad': updataMsgJson['isPad'],
                       'updateNecess': updataMsgJson['updateNecess'],
                       'appPort': updataMsgJson['appPort'],
                       'appSize': self.total_size,
                       'resourceId': self.fileId}

    def upAppZtc(self, push2ZTSC, uploadAppPath, urlRealse, urlUpMsgRealse, updataMsgJson):
        # app 本地地址
        self.uploadAppPath = uploadAppPath
        # APP 上传服务器地址
        self.urlRealse = urlRealse
        result, msg = self.upLoadAppZtsc(push2ZTSC)
        if result.code == bResp.TASK_RESULT_CODE.RESULT_OK:
            self.getMsg(updataMsgJson)
            return self.upDateAppMsg(urlUpMsgRealse)
        else:
            return result, msg


# class aaaa:
#
#     def aa(self):
#         for i in range(100):
#             # 计算下载百分比
#             per = (i + 1) * 100 / 100
#             # 打印进度条（\r是将光标移动到行的开始，会覆盖上一次打印的内容，形成动态打印）
#             # print("\rdownload %s%.f%s" % ('#' * int(per), per, '%'), flush=True, end='')
#             print("\r %s" % per, flush=True, end='')
#             time.sleep(0.002)  # 1024


if __name__ == '__main__':
    appUpload().upLoadAppZtsc(uploadAppPath=appUpload)
    # for c in range(10):
    #     aaaa().aa()
