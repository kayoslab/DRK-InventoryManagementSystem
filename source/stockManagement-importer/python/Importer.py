# -*- coding: utf-8 -*-
#################################################################################
#
# 
#
#################################################################################

from DatabaseObjects import DatabaseObjects
import csv
import time
import datetime
import re
from sets import Set
# just for testing:
import os

class Importer:
    dbo = DatabaseObjects()
    timeFormat = "'%Y-%m-%d %H:%M:%S'"
    importPath = ""
    exportPath = ""
    # CSV Data
    readData = None
    # ParsedString
    sqlString = ""

    def __init__(self, importPath, exportPath):
        self.importPath = importPath
        self.exportPath = exportPath
        if self.readCSV():
            self.writeSQL()


    def getCSVReader(self):
        with open(self.importPath) as csvfile:
            reader = csv.DictReader(csvfile,delimiter=';')
            data = []
            for row in reader:
                data.append(row)
            return data

    def readCSV(self):
        self.readData = self.getCSVReader()
        if self.readData != None:
            return True
        else:
            return False

    def writeSQL(self):
        locations = Set()
        stockObjects = []
        stockValues = []
        for row in self.readData:
            # Location
            locations.add(row['Lagerort'])
            # StockObject
            stockObject = {"title":row['Titel'], "description":row['Beschreibung'], "type":row['Art'], "minimumStock":row["Mindestbestand"], "quotaStock":row["Sollbestand"], "batchSize":row["Losgröße"], "totalVolume":row["Bestand"], "mtkIntervall":row["MTK Intervall"], "stkIntervall":row["STK Intervall"], "location":row["Lagerort"]}
            stockObjects.append(stockObject)
            # StockValue
            stockValue = {"volume":row['Bestand'], "date":row['Datum'], "mtkDate":row['Letzte MTK'], "stkDate":row['Letzte STK'], "inventoryNumber":row['Inventarnummer'], "serialNumber":row['Seriennummer'], "umdns":row['UMDNS'], "batchNumber":row['Chargennummer'], "title":row['Titel'], "location":row['Lagerort']}
            stockValues.append(stockValue)
        if len(locations) > 0:
            locationsString = self.generateLocationSQL(locations)
            if locationsString != None:
                self.sqlString += str(locationsString) + "\n\n\n"
        if len(stockObjects) > 0:
            stockObjectsString = self.generateStockObjectSQL(stockObjects)
            if stockObjectsString != None:
                self.sqlString += str(stockObjectsString) + "\n\n\n"
        if len(stockValues) > 0:
            stockValuesString = self.generateStockValueSQL(stockValues)
            if stockValuesString != None:
                self.sqlString += str(stockValuesString) + "\n\n\n"

        outputFile = open(self.exportPath, 'w')
        outputFile.write(self.sqlString)
        outputFile.close

    def generateLocationSQL(self, locations):
        locationSqlString = "INSERT IGNORE INTO " + str(self.dbo.locationTable["table"]) + "\n(" + self.dbo.locationTable["title"] + ")\nVALUES\n"
        for location in locations:
            locationSqlString += "('" + location + "'),\n"
        # Stripping last \n and ,
        locationSqlString = locationSqlString[:-2]
        # and replace with ; and \n's
        locationSqlString += ";"
        return locationSqlString

    def generateStockObjectSQL(self, stockObjects):
        stockObjectSqlString = "INSERT IGNORE INTO " + str(self.dbo.stockObjectTable["table"]) + "\n(" + str(self.dbo.stockObjectTable["title"]) + ",\n " + str(self.dbo.stockObjectTable["description"]) + ",\n " + str(self.dbo.stockObjectTable["minimumStock"]) + ",\n " + str(self.dbo.stockObjectTable["quotaStock"]) + ",\n " + str(self.dbo.stockObjectTable["batchSize"]) + ",\n " + str(self.dbo.stockObjectTable["totalVolume"]) + ",\n " + str(self.dbo.stockObjectTable["mtkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["stkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["silencedWarnings"]) + ",\n " + str(self.dbo.stockObjectTable["typeId"]) + ")\nVALUES\n"
        for stockObject in stockObjects:
            minimumStock = "0"
            quotaStock = "0"
            batchSize = "0"
            totalVolume = "0"
            mtkIntervall = "0"
            stkIntervall = "0"
            silencedWarnings = "false"
            typeId = str(self.getTypeIdSelect(stockObject["type"]))

            if stockObject["minimumStock"] != None and stockObject["minimumStock"].isdigit():
                minimumStock = str(stockObject["minimumStock"])
            if stockObject["quotaStock"] != None and stockObject["quotaStock"].isdigit():
                quotaStock = str(stockObject["quotaStock"])
            if stockObject["batchSize"] != None and stockObject["batchSize"].isdigit():
                batchSize = str(stockObject["batchSize"])
            if stockObject["totalVolume"] != None and stockObject["totalVolume"].isdigit():
                totalVolume = str(stockObject["totalVolume"])
            if stockObject["mtkIntervall"] != None and stockObject["mtkIntervall"].isdigit():
                mtkIntervall = str(stockObject["mtkIntervall"])
            if stockObject["stkIntervall"] != None and stockObject["stkIntervall"].isdigit():
                stkIntervall = str(stockObject["stkIntervall"])
            # appending Strings
            stockObjectSqlString += "('" + stockObject["title"] + "', '" + stockObject["description"] + "', " + minimumStock + ", " + quotaStock + ", " + batchSize + ", " + totalVolume + ", " + mtkIntervall + ", " + stkIntervall + ", " + silencedWarnings + ", " + typeId + "),\n"
        # Stripping last \n and ,
        stockObjectSqlString = stockObjectSqlString[:-2]
        # and replace with ; and \n's
        stockObjectSqlString += ";"
        return stockObjectSqlString

    def generateStockValueSQL(self, stockValues):
        stockValueSqlString = "INSERT IGNORE INTO " + str(self.dbo.stockValueTable["table"]) + "\n(" + str(self.dbo.stockValueTable["volume"]) + ", " + str(self.dbo.stockValueTable["date"]) + ", " + str(self.dbo.stockValueTable["mtkDate"]) + ", " + str(self.dbo.stockValueTable["stkDate"]) + ", " + str(self.dbo.stockValueTable["inventoryNumber"]) + ", " + str(self.dbo.stockValueTable["serialNumber"]) + ", " + str(self.dbo.stockValueTable["umdns"]) + ", " + str(self.dbo.stockValueTable["batchNumber"]) + ", " + str(self.dbo.stockValueTable["creation"]) + ", " + str(self.dbo.stockValueTable["escalationAck"]) + ", " + str(self.dbo.stockValueTable["stockObjectId"]) + ", " + str(self.dbo.stockValueTable["locationId"]) + ", " + str(self.dbo.stockValueTable["messageId"]) + ")\nVALUES\n"

        for stockValue in stockValues:
            volume = "0"
            date = "null"
            mtkDate = "null"
            stkDate = "null"
            inventoryNumber = "null"
            serialNumber = "null"
            umdns = "null"
            batchNumber = "null"
            creation = time.strftime(self.timeFormat)
            escalationAck = "0"
            stockObjectId = "0"
            locationId = "0"
            messageId = "1"
            if stockValue["volume"] != None and stockValue["volume"].isdigit():
                volume = str(stockValue["volume"])
            if stockValue["date"] != None:
                date = self.getNormalizedDateString(str(stockValue["date"]))
            if stockValue["mtkDate"] != None:
                mtkDate = self.getNormalizedDateString(str(stockValue["mtkDate"]))
            if stockValue["stkDate"] != None:
                stkDate = self.getNormalizedDateString(str(stockValue["stkDate"]))
            if stockValue["inventoryNumber"] != None:
                inventoryNumber = str(stockValue["inventoryNumber"])
            if stockValue["serialNumber"] != None:
                serialNumber = str(stockValue["serialNumber"])
            if stockValue["umdns"] != None:
                umdns = str(stockValue["umdns"])
            if stockValue["batchNumber"] != None:
                batchNumber = str(stockValue["batchNumber"])
            if stockValue["title"] != None:
                stockObjectId = self.getStockObjectSelectStatement(stockValue["title"])
            if stockValue["location"] != None:
                locationId = self.getLocationSelectStatement(stockValue["location"])

            if stockValue["volume"] != None and stockValue["title"] != None and stockValue["location"] != None:
                # appending Strings
                stockValueSqlString += "(" + volume + ", " + date + ", " + mtkDate + ", " + stkDate + ", '" + inventoryNumber + "', '" + serialNumber + "', '" + umdns + "', '" + batchNumber + "', " + creation + ", " + escalationAck + ", " + stockObjectId + ", " + locationId + ", " + messageId + "),\n"

        if len(stockValueSqlString) == 0:
            return None
        # Stripping last \n and ,
        stockValueSqlString = stockValueSqlString[:-2]
        # and replace with ; and \n's
        stockValueSqlString += ";"
        return stockValueSqlString

    # normalizes a given date String and
    # reformat the string to the VALUE
    # our MySQL Database expects
    def getNormalizedDateString(self, dateString):
        if dateString != "":
            #################################################################################
            # The following RegEx would be good enough, but wouldn't contain leap years
            # ^(0?[1-9]|[12][0-9]|3[01])[\/](0?[1-9]|1[012])[\]\d{4}$
            # pattern for 20/02/2016
            # Could also loop over ["\/","\-","\."] but this tells more of a story
            #################################################################################
            pattern = re.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\/]" + "(0[1-9]|1[012]))|((29|30|31)[\/]" + "(0[13578]|1[02]))|((29|30)[\/]" + "(0[4,6,9]|11)))[\/]" + "(19|[2-9][0-9])\d\d$)|" + "(^29[\/]" + "02[\/]" + "(19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%d/%m/%Y").strftime(self.timeFormat)
                return date
            # pattern for 20-02-2016
            pattern = re.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\-]" + "(0[1-9]|1[012]))|((29|30|31)[\-]" + "(0[13578]|1[02]))|((29|30)[\-]" + "(0[4,6,9]|11)))[\-]" + "(19|[2-9][0-9])\d\d$)|" + "(^29[\-]" + "02[\-]" + "(19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%d-%m-%Y").strftime(self.timeFormat)
                return date
            # pattern for 20.02.2016
            pattern = re.compile("(^(((0[1-9]|1[0-9]|2[0-8])[\.]" + "(0[1-9]|1[012]))|((29|30|31)[\.]" + "(0[13578]|1[02]))|((29|30)[\.]" + "(0[4,6,9]|11)))[\.]" + "(19|[2-9][0-9])\d\d$)|" + "(^29[\.]" + "02[\.]" + "(19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%d-%m-%Y").strftime(self.timeFormat)
                return date
            # pattern for 02/2016
            pattern = re.compile("^(0?[1-9]|1[012])[\/]\d{4}$")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%m/%Y").strftime(self.timeFormat)
                return date
            # pattern for 02-2016
            pattern = re.compile("^(0?[1-9]|1[012])[\/]\d{4}$")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%m/%Y").strftime(self.timeFormat)
                return date
            # pattern for 02-2016
            pattern = re.compile("^(0?[1-9]|1[012])[\.]\d{4}$")
            if pattern.match(dateString):
                date = datetime.datetime.strptime(dateString, "%m.%Y").strftime(self.timeFormat)
                return date
        return "null"

    def getStockObjectSelectStatement(self, stockObjectTitleString):
        return "(SELECT " + str(self.dbo.stockObjectTable["id"]) + " FROM " + str(self.dbo.stockObjectTable["table"]) + " WHERE "  + str(self.dbo.stockObjectTable["title"]) + " = '" + str(stockObjectTitleString) + "' LIMIT 1)"

    def getLocationSelectStatement(self, locationTitleString):
        return "(SELECT " + str(self.dbo.locationTable["id"]) + " FROM " + str(self.dbo.locationTable["table"]) + " WHERE "  + str(self.dbo.locationTable["title"]) + " = '" + str(locationTitleString) + "' LIMIT 1)"

    # get type id from String
    def getTypeIdSelect(self, typeString):
        if typeString == "1":
            return typeString
        elif typeString == "2":
            return typeString
        elif typeString == "3":
            return typeString
        elif typeString == "4":
            return typeString
        elif typeString.lower()  == "Gerät".lower() :
            return "1"
        elif typeString.lower() == "Device".lower() :
            return "1"
        elif typeString.lower() == "MedMat".lower() :
            return "2"
        elif typeString.lower() == "Medical Material".lower() :
            return "2"
        elif typeString.lower() == "MedicalMaterial".lower() :
            return "2"
        elif typeString.lower() == "ConsMat".lower() :
            return "3"
        elif typeString.lower() == "Consumable Material".lower() :
            return "3"
        elif typeString.lower() == "ConsumableMaterial".lower() :
            return "3"
        elif typeString.lower() == "Versorgungsmaterial".lower() :
            return "3"
        return "4"

# also for testing, gets current directory of Importer.py
dir_path = os.path.dirname(os.path.realpath(__file__))
i = Importer(dir_path + "/data.csv", dir_path + "/data.sql")
