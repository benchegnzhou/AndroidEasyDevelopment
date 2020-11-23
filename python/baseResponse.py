from enum import Enum
from enum import unique

'''
通用的返回响应
'''



@unique  # unique以装饰器形式使用
class TASK_RESULT_CODE(Enum):
    RESULT_OK = 200,
    RESULT_ERROR = 0


class taskRespose():
    def __init__(self):
        self.code = TASK_RESULT_CODE.RESULT_OK
        self.msg = "操作成功"

    def markResultSuccess(self, msg="操作成功"):
        '''
        标记操作成功
        :param msg:
        :return:
        '''
        self.code = TASK_RESULT_CODE.RESULT_OK
        self.msg = msg

    def markResultFail(self, msg="操作失败"):
        '''
        标记操作成功
        :param msg:
        :return:
        '''
        self.code = TASK_RESULT_CODE.RESULT_ERROR
        self.msg = msg
