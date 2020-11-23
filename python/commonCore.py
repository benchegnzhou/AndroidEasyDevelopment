import os

# path1=os.path.abspath('.')#获取当前脚本所在的路径
# path2=os.path.abspath('..')#获取当前脚本所在路径的上一级路径

# 获取当前文件路径				G:\house\app\android\python\upload_file_property.py
current_path = os.path.abspath(__file__)
# 获取当前文件的父目录 			G:\house\app\android\python
father_path = os.path.abspath(os.path.dirname(current_path) + os.path.sep + ".")
# 获取当前文件的父目录的父目录 	G:\house\app\android
root_path = os.path.abspath(os.path.dirname(father_path) + os.path.sep + ".")


def makeDir(filePath):
    '''
    创建文件夹
    '''
    if not os.path.exists(filePath):
        os.makedirs(filePath)
