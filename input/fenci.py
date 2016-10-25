# encoding: utf-8

import sys
import csv
import jieba
import jieba.posseg as pseg

def main():
	reload(sys)
	sys.setdefaultencoding( "utf-8" )
	f = open("中国数据库中国公司对应专利.csv")
	line = f.readline()
	while line:  
		line = f.readline()  
		seg_list = jieba.cut(line, cut_all=False)
		S =  " ".join(seg_list)
		print S[:-1]
	f.close()  

if __name__ == '__main__':
	main()
