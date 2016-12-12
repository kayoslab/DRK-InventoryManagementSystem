# -*- coding: utf-8 -*-
from DatabaseObjects import DatabaseObjects
import csv
from sets import Set

class Importer:
    dbo = DatabaseObjects()
    importPath = ""
    exportPath = ""
    fileData = None
    sqlString = ""
    def __init__(self, importPath, exportPath):
        self.importPath = importPath
        self.exportPath = exportPath

    def getCSVReader(self):
        with open(self.importPath) as csvfile:
            reader = csv.DictReader(csvfile,delimiter=';')
            data = []
            for row in reader:
                data.append(row)
            return data

    def readCSV(self):
        self.fileData = self.getCSVReader()
        if self.fileData != None:
            return True
        else:
            return False

    def writeSQL(self):
        locations = Set()
        stockObjects = []
        for row in self.fileData:
            locations.add(row['Lagerort'])
            stockObject = {"title":row['Titel'], "description":row['Beschreibung'], "type":row['Art'], "minimumStock":row["Mindestbestand"], "quotaStock":row["Sollbestand"], "batchSize":row["Losgröße"], "totalVolume":row["Bestand"], "mtkIntervall":row["MTK Intervall"], "stkIntervall":row["STK Intervall"], "location":row["Lagerort"]}
            stockObjects.append(stockObject)
        if len(locations) > 0:
            self.generateLocationSQL(locations)
        if len(stockObjects) > 0:
            self.generateStockObjectSQL(stockObjects)

    def generateLocationSQL(self, locations):
        sqlString = "INSERT INTO " + str(self.dbo.locationTable["table"]) + "\n(" + self.dbo.locationTable["title"] + ")\nVALUES\n"
        for location in locations:
            sqlString += "('" + location + "'),\n"
        sqlString = sqlString[:-2]
        sqlString += ";"
        print(sqlString)

    def generateStockObjectSQL(self, stockObjects):
        sqlString = "INSERT INTO " + str(self.dbo.stockObjectTable["table"]) + "\n(" + str(self.dbo.stockObjectTable["title"]) + ",\n " + str(self.dbo.stockObjectTable["description"]) + ",\n " + str(self.dbo.stockObjectTable["minimumStock"]) + ",\n " + str(self.dbo.stockObjectTable["quotaStock"]) + ",\n " + str(self.dbo.stockObjectTable["batchSize"]) + ",\n " + str(self.dbo.stockObjectTable["totalVolume"]) + ",\n " + str(self.dbo.stockObjectTable["mtkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["stkIntervall"]) + ",\n " + str(self.dbo.stockObjectTable["silencedWarnings"]) + ",\n " + str(self.dbo.stockObjectTable["typeId"]) + ")\nVALUES\n"
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

            sqlString += "('" + stockObject["title"] + "', '" + stockObject["description"] + "', " + minimumStock + ", " + quotaStock + ", " + batchSize + ", " + totalVolume + ", " + mtkIntervall + ", " + stkIntervall + ", " + silencedWarnings + ", " + typeId + "),\n"

        sqlString = sqlString[:-2]
        sqlString += ";"
        print(sqlString)

    def getNormalizedDateString(self, dateString):
        return "null"

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
        return "4"

    def generateStockValueSQL(self, stockValues):
        sqlString = "INSERT INTO " + str(self.dbo.stockValueTable["table"]) + "\n(title)\nVALUES\n"

        sqlString = sqlString[:-2]
        sqlString += ";"
        print(sqlString)


i = Importer("/Users/cr0ss/Desktop/Medikamente.csv", "~/Documents/Medikamente.sql")
if i.readCSV():
    i.writeSQL()
