# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class GithubdataItem(scrapy.Item):
    title = scrapy.Field()
    link = scrapy.Field()
    desc = scrapy.Field()
    pass

# 大类别 小类别 项目名字 项目链接 项目描述
class crawlItem(scrapy.Item):
    cate = scrapy.Field()
    subcate = scrapy.Field()
    name = scrapy.Field()
    link = scrapy.Field()
    desc = scrapy.Field()
    pass

class FileDownloadPipeline(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    file_urls=scrapy.Field()
    files=scrapy.Field()
