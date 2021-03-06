# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals
#from selenium import webdriver
from scrapy.http import HtmlResponse
import time
import random
#from selenium.webdriver.chrome.options import Options


class GithubdataSpiderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the spider middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_spider_input(self, response, spider):
        # Called for each response that goes through the spider
        # middleware and into the spider.

        # Should return None or raise an exception.
        return None

    def process_spider_output(self, response, result, spider):
        # Called with the results returned from the Spider, after
        # it has processed the response.

        # Must return an iterable of Request, dict or Item objects.
        for i in result:
            yield i

    def process_spider_exception(self, response, exception, spider):
        # Called when a spider or process_spider_input() method
        # (from other spider middleware) raises an exception.

        # Should return either None or an iterable of Response, dict
        # or Item objects.
        pass

    def process_start_requests(self, start_requests, spider):
        # Called with the start requests of the spider, and works
        # similarly to the process_spider_output() method, except
        # that it doesn’t have a response associated.

        # Must return only requests (not items).
        for r in start_requests:
            yield r

    def spider_opened(self, spider):
        spider.logger.info('Spider opened: %s' % spider.name)


class GithubdataDownloaderMiddleware(object):
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.

    @classmethod
    def from_crawler(cls, crawler):
        # This method is used by Scrapy to create your spiders.
        s = cls()
        crawler.signals.connect(s.spider_opened, signal=signals.spider_opened)
        return s

    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        return None

    def process_response(self, request, response, spider):
        # Called with the response returned from the downloader.

        # Must either;
        # - return a Response object
        # - return a Request object
        # - or raise IgnoreRequest
        return response

    def process_exception(self, request, exception, spider):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        pass

    def spider_opened(self, spider):
        spider.logger.info('Spider opened: %s' % spider.name)

class ChromeMiddleware(object):
        def __init__(self):
            option = Options()
            #option.add_argument('--headless')
            self.driver = webdriver.Chrome(executable_path="/usr/local/bin/chromedriver",
                                           chrome_options=option)

        def process_request(self, request, spider):
            self.driver.get(request.url)
            print("页面开始渲染。。。")
            self.driver.execute_script("scroll(0, 1000);")
            time.sleep(1)
            rendered_body = self.driver.page_source
            print("页面完成渲染。。。")
            return HtmlResponse(request.url, body=rendered_body, encoding="utf-8")

        def spider_closed(self, spider, reason):
            print('驱动关闭')
            self.driver.close()


class ProxyMiddleware(object):
    def process_request(self, request, spider):
        #此处需要增加代理池
        proxy_pool = [
            "http://117.114.149.10:43727",
            "http://202.119.248.8:80",
            "http://175.148.75.113:1133",
            "http://119.179.60.117:8118",
            "http://222.182.121.139:8118",
            "http://123.162.168.192:40274",
            "http://27.42.168.46:48919",
            "http://39.81.145.104:20183",
            "http://183.159.85.120:18118",
            "http://125.105.62.17:8118",
            "http://58.218.201.188:58093",
            "http://110.73.11.115:8123",
            "http://113.116.178.58:808",
            "http://182.134.128.233:8118",
            "http://59.172.27.6:53281",
            "http://113.116.176.150:808",
            "http://60.182.199.118:8010",
            "http://182.146.203.217:8118",
            "http://61.145.69.27:42380",
        ]
        a = random.randint(0,len(proxy_pool)-1)
        if request.url.startswith("http://"):

            request.meta['proxy'] = proxy_pool[a]  # http代理
        elif request.url.startswith("https://"):
            request.meta['proxy'] = proxy_pool[a]   # https代理

class RandomUserAgent(object):
    """Randomly rotate user agents based on a list of predefined ones"""

    def __init__(self, agents):
        self.agents = agents

    @classmethod
    def from_crawler(cls, crawler):
        return cls(crawler.settings.getlist('USER_AGENTS'))

    def process_request(self, request, spider):
        #print "**************************" + random.choice(self.agents)
        request.headers.setdefault('User-Agent', random.choice(self.agents))