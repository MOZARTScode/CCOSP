# -*- coding:UTF-8 -*- ＃
from scrapy.spiders import Spider
from scrapy.http import Request
from scrapy.linkextractors import LinkExtractor
import re

import sys
sys.path.append(r'/Users/shuxin/PycharmProjects/githubData/githubData/Util')
from MongoUtil import getUrl

from urllib.parse import urlparse
from urllib.parse import urlunparse
from urllib.parse import urlparse

class fileSpider(Spider):
    name="file_download"
    allowed_domains=['github.com']
    start_urls = getUrl("Platforms")

    def parse(self,response):
        print("start")

        path = urlparse(response.url).path
        projectName = path.split('/')[2]
        with open(projectName+".zip", 'wb') as f:
            f.write(response.body)
        '''    
        for link in le.extract_links(response):
            print(link.url)
            yield Request(link.url)
        '''
#这边需要改进
    def parse_link(self,response):
        pattern=re.compile('href=(.*\.py)')
        div=response.xpath('/html/body/div[4]/div[1]/div/div')
        p=div.xpath('//p')[0].extract()
        link=re.findall(pattern,p)[0]
        if ('/') in link:
            href='http://matplotlib.org/'+link.split('/')[2]+'/'+link.split('/')[3]+'/'+link.split('/')[4]
        else:
            link=link.replace('"','')
            scheme=urlparse(response.url).scheme
            netloc=urlparse(response.url).netloc
            temp=urlparse(response.url).path
            path='/'+temp.split('/')[1]+'/'+temp.split('/')[2]+'/'+link
            combine=(scheme,netloc,path,'','','')
            href=urlunparse(combine)
#            print href,os.path.splitext(href)[1]
        file=FileDownloadItem()
        file['file_urls']=[href]
        return file

