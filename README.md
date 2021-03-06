# CCOSP

使用模型同时进行tag&label的给出，同时进行tag以及label的关联

## 数据层

数据来源:  

  1. 数据集[1]我们自己的爬取数据  
  
  2. 数据集[2]sourcerer提供的数据集 http://sourcerer.ics.uci.edu/#  [70000个Java项目，同时还有他们提取好的数据特征]  
  
  3. 数据集[3]待添加  

#### 爬虫
	githubData文件夹：
		环境：python3.6
		框架：scrapy
		依赖：mongoDB数据，下文installing环境
		
installing：
     
     pip install scrapy
     pip install scrapy_proxies(代理)
     pip install pymongo
	

#### 任务
  
        [王佳辉]  1.数据集的寻找：具有标签以及描述的数据，最好是拥有tag。
           	
             
            
数据要求：  

  同时具有tag 以及 label。若采用：  
  
  数据集[1]:所有的都拥有了label，少量拥有tag。  
  
           处理方式：使用readme进行tag的标注。  
           
           建立模型：使用文本处理方式，使用拥有tag的项目进行验证，模型选取的tag是否有效，如果有效，则可以进行tag的标注。  
           
           原理：只要数据集都按照这个方式进行处理，则进行tag的标注的时候，可以验证模型层的效果是可以的（只不过我们的数据集可能是不太对的）  
           
  数据集[2]：没有具体研究内容，是否已经拥有分类以及tag。
      
#### 任务
       
  	  [舒鑫]1.将所有项目下载下来
  	  [舒鑫]2.给没有tag的模型打上tag：使用readme或者描述（若使用readme则不可以再用readme拿特征）
  	  [王佳辉]3.数据集的寻找：具有标签以及描述的数据，最好是拥有tag。
  
## 特征提取层


#### 任务：
     
     [廖智勇]1.类<->注释内容(双向定位):使用抽象语法树，参考刘伟老师博客(https://blog.csdn.net/LoveLion/article/details/19050155)
     	各个语言的都用一两个测试。
     [舒鑫]2.项目文件后缀名(提取前10个的文件，记录数量)
     [yyx jyf]3.自然语言处理爬虫获得的awesome描述
     [yyx jyf]4.自然语言处理技术readme信息
     [舒鑫]5.包引用(例如：import org.apache.httpclient)
     [舒鑫]6.包名 以及 类名（可以再深入一层分析处理）
     
#### 该层论文文件夹(时间，上传人，论文名，已读人)
paper：  

  时间18-12-1_shuxin：  
  
    A Search Engine For Finding Highly Relevant Applications[已读：]  
    
    Extracting Facts from Open Source Software[已读：]  
    
    Federated Search for Open Source Software Reuse[已读：]  
    
    Improving Automated Documentation to Code Traceability by Combining Retrieval Techniques[已读：]  
    
    Information Delivery in Support of Learning Reusable Software Components on Demand[已读：]  
    
    Linking Documentation and Source Code in a Software Chrestomathy[已读：]  
    
    Mining API Usages from Open Source Repositories[已读：]  
    
    Mining Crowd Wisdom in Open Source Communities[已读：]  
    
    Recommending Source Code Examples via API Call Usages and Documentation[已读：]  
    
    Sourcerer- An internet-scale software repository[已读：]  
    
    Using Structural Context to Recommend Source Code Examples[已读：]  
    
## 模型

 
#### 任务：
     
     [王佳辉]1.寻找mat代码，顶会（可以发邮件问或者找一下）
     [王佳辉]2.寻找相关对我们有用到的论文。

## 相关资料

数据层()[]  

特征提取层()[]  

模型层()[]  

