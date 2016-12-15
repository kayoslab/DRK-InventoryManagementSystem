class DatabaseObjects:
    ################################################
    # GroupRight Table
    groupRightTable = {
    "table":"`GroupRight`",
    "id":"`GroupRight`.`id`",
    "title":"`GroupRight`.`title`"
    }
    # Group Table
    groupTable = {
    "table":"`Group`",
    "id":"`Group`.`id`",
    "title":"`Group`.`title`",
    "isActive":"`Group`.`isActive`"
    }
    # GroupHasRights Table
    groupHasRightsTable = {
    "table":"`GroupHasRights`",
    "group":"`GroupHasRights`.`group`",
    "right":"`GroupHasRights`.`right`"
    }
    # User Table
    userTable = {
    "table":"`User`",
    "id":"`User`.`id`",
    "username":"`User`.`username`",
    "firstname":"`User`.`firstname`",
    "name":"`User`.`name`",
    "mail":"`User`.`mail`",
    "creation":"`User`.`creation`",
    "password":"`User`.`password`",
    "passwordChanged":"`User`.`passwordChanged`"
    }
    # UserIsMemberOfGroup Table
    userIsMemberOfGroupTable = {
    "table":"`UserIsMemberOfGroup`",
    "user":"`UserIsMemberOfGroup`.`user`",
    "group":"`UserIsMemberOfGroup`.`group`"
    }
    # Location Table
    locationTable = {
    "table":"`Location`",
    "id":"`Location`.`id`",
    "title":"`Location`.`title`"
    }
    # Message Table
    messageTable = {
    "table":"`Message`",
    "id":"`Message`.`id`",
    "title":"`Message`.`title`",
    }
    # Type Table
    typeTable = {
    "table":"`Type`",
    "id":"`Type`.`id`",
    "title":"`Type`.`title`"
    }
    # StockObject Table
    stockObjectTable = {
    "table":"`StockObject`",
    "id":"`StockObject`.`id`",
    "title":"`StockObject`.`title`",
    "description":"`StockObject`.`description`",
    "batchSize":"`StockObject`.`batchSize`",
    "totalVolume":"`StockObject`.`totalVolume`",
    "mtkIntervall":"`StockObject`.`mtkIntervall`",
    "stkIntervall":"`StockObject`.`stkIntervall`",
    "creation":"`StockObject`.`creation`",
    "typeId":"`StockObject`.`typeId`"
    }
    # StockValue Table
    stockValueTable = {"table":"`StockValue`",
    "id":"`StockValue`.`id`",
    "volume":"`StockValue`.`volume`",
    "date":"`StockValue`.`date`",
    "mtkDate":"`StockValue`.`mtkDate`",
    "stkDate":"`StockValue`.`stkDate`",
    "inventoryNumber":"`StockValue`.`inventoryNumber`",
    "serialNumber":"`StockValue`.`serialNumber`",
    "umdns":"`StockValue`.`umdns`",
    "batchNumber":"`StockValue`.`batchNumber`",
    "minimumStock":"`StockValue`.`minimumStock`",
    "quotaStock":"`StockValue`.`quotaStock`",
    "silencedWarnings":"`StockValue`.`silencedWarnings`",
    "creation":"`StockValue`.`creation`",
    "stockObjectId":"`StockValue`.`stockObjectId`",
    "locationId":"`StockValue`.`locationId`",
    "messageId":"`StockValue`.`messageId`"
    }
    # Operation Table
    operationTable = {
    "table":"`Operation`",
    "id":"`Operation`.`id`",
    "title":"`Operation`.`title`"
    }
    # Logbook Table
    logbookTable = {
    "table":"`Logbook`",
    "id":"`Logbook`.`id`",
    "title":"`Logbook`.`title`",
    "date":"`Logbook`.`id`",
    "userId":"`Logbook`.`title`",
    "stockObjectId":"`Logbook`.`id`",
    "operationId":"`Logbook`.`title`"
    }
    ################################################
