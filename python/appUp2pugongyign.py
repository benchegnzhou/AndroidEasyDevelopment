import requests
import json
import baseResponse as bResp
import operationRecords as records
import loggerLocal as log
import time
import os


'''
APP上传蒲公英
'''
requests.adapters.DEFAULT_RETRIES = 5  # 增加重连次数


class AppUpPGY():
    def __init__(self):
        self.uploadSuccess = False
        self.total_size = 0
        self.appPath = ""

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

    def upAppToPugognying(self, push2PGY, appPath, appVersionName, uKey, _api_key, updateDescription):
        '''
        将这个APP上传蒲公英
        :param push2ZTSC:
        :param appPath:
        :param uKey:
        :param _api_key:
        :param updateDescription:
        :return:
        '''

        result = bResp.taskRespose()
        result.markResultSuccess("成功")
        log.loggerP.printMsgOneLineCenterCommon("*")
        log.loggerP.printMsgOneLineCenterCommonCustomSignal("APP上传蒲公英")

        try:
            if not push2PGY:
                result.markResultFail("配置文件中关闭了上传蒲公英服务器权限")
                return False

            # (必填)应用安装方式，值为(2,3)。2：密码安装，3：邀请安装
            buildInstallType = 1
            # buildPassword = '123456'
            murl = 'https://www.pgyer.com/apiv2/app/upload'
            print("*\n" * 3, end="")
            log.loggerP.printMsgOneLineCenterCommon("")
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("APP上传蒲公英")
            self.total_size = os.path.getsize(appPath)
            print("*\n" * 4, end="")
            print("* 待上传apk文件路径 %s" % appPath)
            print("* 待上传apk文件版本 %s" % appVersionName)
            print("* 待上传apk文件大小 %s" % (self.total_size / 1024 / 1024))
            print("* 正在上传请稍后...")
            print("*\n" * 2, end="")
            data = {
                'uKey': uKey,
                '_api_key': _api_key,
                'buildInstallType': buildInstallType,
                # 'buildPassword': buildPassword,
                'buildUpdateDescription': updateDescription
            }

            proxies = {"http": "127.0.0.1:8888","https": "127.0.0.1:8888"}
            files = { 'params': json.dumps(data), 'file': open(appPath, 'rb')}



            # 关闭多余的链接： https://www.cnblogs.com/caicaihong/p/7495435.html
            # s = requests.session()
            # s.keep_alive = False
            response = requests.post(murl, data=data, files=files)
            # response = requests.post(murl, data=m, headers=req_headers, verify=False, proxies =proxies)
            # print(response.text)
            # records.writeText(response.text)
            res = json.loads(response.text)

            print(res)
            if res['code'] == 0:
                print('*\n' * 2, end="")
                print("* 文件上传蒲公英成功，日志如下")
                self.uploadSuccess = True
                result.markResultSuccess("文件上传蒲公英成功")
                print('*\n' * 2, end="")
            else:
                print('*\n' * 2, end="")
                result.markResultFail("文件上传蒲公英失败")
                print("* 文件上传蒲公英失败")
                self.uploadSuccess = False

        except Exception as err:
            print('*\n' * 2, end="")
            result.markResultFail("文件上传蒲公英失败")
            print(err)
            print('*\n' * 2, end="")
            print("* 文件上传蒲公英失败")

        finally:
            log.loggerP.printMsgOneLineCenterCommon("*")
            return result
