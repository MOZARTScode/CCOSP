# CCOSP

使用模型同时进行tag&label的给出，同时进行tag以及label的关联

## 数据层

数据来源：
  1. 数据集[1]我们自己的爬取数据
  2. 数据集[2]sourcerer提供的数据集 http://sourcerer.ics.uci.edu/#  [70000个Java项目，同时还有他们提取好的数据特征]
  3. 数据集[3]待添加
 
数据要求：
  同时具有tag 以及 label。若采用：
  数据集[1]:所有的都拥有了label，少量拥有tag。
           处理方式：使用readme进行tag的标注。
           建立模型：使用文本处理方式，使用拥有tag的项目进行验证，模型选取的tag是否有效，如果有效，则可以进行tag的标注。
           原理：只要数据集都按照这个方式进行处理，则进行tag的标注的时候，可以验证模型层的效果是可以的（只不过我们的数据集可能是不太对的）
  数据集[2]：没有具体研究内容，是否已经拥有分类以及tag。         
## 特征提取层

#### 特征提取文件夹(时间，上传人，论文名，已读人)
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

尚未开始

## 相关资料

数据层()[]
特征提取层()[]
模型层()[]
