# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

from scrapy.pipelines.files import FilesPipeline
from urllib.parse import urlparse


class GithubdataPipeline(object):
    def process_item(self, item, spider):
        return item


class FileDownloadPipeline(FilesPipeline):
    def file_path(self, request, response=None, info=None):
        path = urlparse(request.url).path
        projectName = path.split('/')[2]
        return 'projects/%s' % (projectName)

