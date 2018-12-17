import pymongo
import bson
import re


def getUrl(Platforms):
    client = pymongo.MongoClient(host='localhost', port=27017)
    db = client.dbname
    collection = db.repository_list

    pattern = re.compile("^"+Platforms+"/")
    regex = bson.regex.Regex.from_native(pattern)
    regex.flags ^= re.UNICODE

    a = []
    for result in collection.find({"category": regex}):
        a.append(result['link']+"/archive/master.zip")
    return a

if __name__ == "__main__":
    '''
    client = pymongo.MongoClient(host='localhost', port=27017)
    db = client.dbname
    collection = db.repository_list

    pattern = re.compile("^Platforms/")
    regex = bson.regex.Regex.from_native(pattern)
    regex.flags ^= re.UNICODE
    count = 1
    #for result in collection.find({"category": regex}):
    #    print(result['category']+"----"+result['link'])
    #    count+=1
    #print(count)
    result = collection.find({"category": regex})
    print(len(result))
    '''
    result = getUrl("Platforms")
    for url in result:
        print(url)
