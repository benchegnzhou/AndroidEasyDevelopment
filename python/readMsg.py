import json
import baseResponse as bResp
import loggerLocal as log
import commonCore as common


class ReadMSg():
    '''
     版本更新信息读取
    '''

    def __init__(self):
        self.operation = ""
        pass

    def readUpMsg(self, root_path):
        '''
        版本更新信息读取
        :return:
        '''

        result = bResp.taskRespose()
        result.markResultSuccess("成功")

        try:
            print('\n' * 4)
            log.loggerP.printMsgOneLineCenterCommon("")
            log.loggerP.printMsgOneLineCenterCommonCustomSignal("读取配置文件")
            print("*\n" * 2, end="")
            print("* 配置文件路径     %s\\app\\build\outputs\\versionMsg.properties" % (root_path))
            print('*\n' * 2, end="")
            with open('%s\\app\\build\outputs\\versionMsg.properties' % (root_path), 'r', encoding='UTF-8') as f:
                # print("--------file_object path"+root_path+'/src/versionMsg.properties')
                msg = f.read()
                msg = msg.replace('\n', "\\n")
                print("* 配置信息")
                print(msg)
                print('*\n' * 2, end="")
                self.operation = json.loads(msg)
                print('*\n' * 2, end="")
        except Exception as e:
            result.markResultFail("配置文件读取失败")
            print(e)
        finally:
            log.loggerP.printMsgOneLineCenterCommon("")
            return result,self.operation

re = ReadMSg()

if __name__ == '__main__':

    re.readUpMsg(common.root_path)
