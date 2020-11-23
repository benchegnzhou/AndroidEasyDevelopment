from textwrap import fill

class LoggerPrint:
    '''
    屏幕日志打印
    '''

    def __init__(self):
        self.countChar = 5

    def loggerInit(self, charCount):
        self.countChar = charCount

    def printStar(self, num):
        if num < 1:
            print("")
        print("*" * num)

    def printMsgOneLineCenter(self, msg="", countChar=50):
        print(msg.center(countChar, '*'))

    def printMsgOneLineCenterCommon(self, msg=""):
        print(msg.center(self.countChar, '*'))


    def printMsgOneLineCenterCommonCustomSignal(self, msg=""):
        print(msg.center(self.countChar, ' '))


loggerP = LoggerPrint()

if __name__ == '__main__':

    loggerP.loggerInit(50)
    loggerP.printStar(50)
    loggerP.printMsgOneLineCenterCommon("当前版本信息")
    loggerP.printMsgOneLineCenterCommon("")
    loggerP.printMsgOneLineCenterCommon("阿萨德a阿斯达所sd做的饭")

