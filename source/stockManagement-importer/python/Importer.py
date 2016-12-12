# -*- coding: utf-8 -*-
from DatabaseObjects import DatabaseObjects
import csv
import time
from sets import Set
# just for testing:
import os

class Importer:
    dbo = DatabaseObjects()
    timeFormat = "%Y-%m-%d %H:%M:%S"
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
            stockValue = {}
            stockValues.append(stockValue)
        if len(locations) > 0:
            self.sqlString += str(self.generateLocationSQL(locations))
        if len(stockObjects) > 0:
            self.sqlString += str(self.generateStockObjectSQL(stockObjects))
        if len(stockValues) > 0:
            self.sqlString += str(self.generateStockValueSQL(stockValues))
        outputFile = open(self.exportPath, 'w')
        outputFile.write(self.sqlString)
        outputFile.close

    def generateLocationSQL(self, locations):
        locationSqlString = "// Location Insert Statement \n" + "INSERT INTO " + str(self.dbo.locationTable["table"]) + "\n(" + self.dbo.locationTable["title"] + ")\nVALUES\n"
        for location in locations:
            locationSqlString += "('" + location + "'),\n"
        # Stripping last \n and ,
        locationSqlString = locationSqlString[:-2]
        # and replace with ; and \n's
        locationSqlString += ";\n\n\n"
        return locationSqlString

    def generateStockObjectSQL(self, stockObjects):
        stockObjectSqlString = "// StockObject Insert Statement \n" + "INSERT INTO " + str(self.dbo.stockObjectTable["table"]) + "\n(" + str(self.dbo.stockObjectTable["title"]) + ",\n " + str(self.dbo.stockObjectTable["description"]) + ",\n " + str(self.dbo.stockObjectTable["minimumStock"]) + ",\n " + str(self.dbo.stockObjectTable["quotaStock"]) + ",\n " + str(self.dbo.stockObjectTable["batchSize"]) + ",\n " + str(self.dbo.stockObjectTable["totalVolume"]) + ",\n " + str(self.dbo.stockObjectTable["mtkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["stkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["silencedWarnings"]) + ",\n " + str(self.dbo.stockObjectTable["typeId"]) + ")\nVALUES\n"
        for stockObject in stockObjects:
            minimumStock = "0"
            quotaStock = "0"
            batchSize = "0"
            totalVolume = "0"
            mtkIntervall = "0"
            stkIntervall = "0"
            silencedWarnings = "false"
            typeId = str(self.getTypeIdSelect(stockObject["type"]))

            if stockObject["minimumStock"] != None and type(stockObject["minimumStock"]) == int:
                minimumStock = str(stockObject["minimumStock"])
            if stockObject["quotaStock"] != None and type(stockObject["quotaStock"]) == int:
                quotaStock = str(stockObject["quotaStock"])
            if stockObject["batchSize"] != None and type(stockObject["batchSize"]) == int:
                batchSize = str(stockObject["batchSize"])
            if stockObject["totalVolume"] != None and type(stockObject["totalVolume"]) == int:
                totalVolume = str(stockObject["totalVolume"])
            if stockObject["mtkIntervall"] != None and type(stockObject["mtkIntervall"]) == int:
                mtkIntervall = str(stockObject["mtkIntervall"])
            if stockObject["stkIntervall"] != None and type(stockObject["stkIntervall"]) == int:
                stkIntervall = str(stockObject["stkIntervall"])
            # appending Strings
            stockObjectSqlString += "('" + stockObject["title"] + "', '" + stockObject["description"] + "', " + minimumStock + ", " + quotaStock + ", " + batchSize + ", " + totalVolume + ", " + mtkIntervall + ", " + stkIntervall + ", " + silencedWarnings + ", " + typeId + "),\n"
        # Stripping last \n and ,
        stockObjectSqlString = stockObjectSqlString[:-2]
        # and replace with ; and \n's
        stockObjectSqlString += ";\n\n\n"
        return stockObjectSqlString

    def generateStockValueSQL(self, stockValues):
        stockValueSqlString = "// StockValue Insert Statement \n" + "INSERT INTO " + "\n(" + str(self.dbo.stockValueTable["table"]) + str(self.dbo.stockValueTable["volume"]) + ", " + str(self.dbo.stockValueTable["date"]) + ", " + str(self.dbo.stockValueTable["mtkDate"]) + ", " + str(self.dbo.stockValueTable["stkDate"]) + ", " + str(self.dbo.stockValueTable["inventoryNumber"]) + ", " + str(self.dbo.stockValueTable["serialNumber"]) + ", " + str(self.dbo.stockValueTable["umdns"]) + ", " + str(self.dbo.stockValueTable["batchNumber"]) + ", " + str(self.dbo.stockValueTable["creation"]) + ", " + str(self.dbo.stockValueTable["escalationAck"]) + ", " + str(self.dbo.stockValueTable["stockObjectId"]) + ", " + str(self.dbo.stockValueTable["locationId"]) + ", " + str(self.dbo.stockValueTable["messageId"]) + ")\nVALUES\n"

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
            # appending Strings
            stockValueSqlString += "(" + volume + "," + date + "," + mtkDate + "," + stkDate + "," + inventoryNumber + "," + serialNumber + "," + umdns + "," + batchNumber + "," + creation + "," + escalationAck + "," + stockObjectId + "," + locationId + "," + messageId + "," + "),\n"
        # Stripping last \n and ,
        stockValueSqlString = stockValueSqlString[:-2]
        # and replace with ; and \n's
        stockValueSqlString += ";\n\n\n"
        return stockValueSqlString

    # normalizes a given date String and
    # reformat the string to the VALUE
    # our MySQL Database expects
    def getNormalizedDateString(self, dateString):
        return "null"

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
