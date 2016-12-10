<body>
    <?php include 'nav.php'?>
    <div class = "container">
        </br>
        <?php
                include 'connect.php';
                echo "</br> \n </br> \n";

                $sql = "SELECT
                        `sv`.`id`,
                        `so`.`id` as `object`,
                        `so`.`title`,
                        `sv`.`volume`,
                        `lo`.`title` as `location`,
                        `sv`.`messageId`,
                        `so`.`typeId`
                        FROM `StockValue` `sv`
                        INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`)
                        INNER JOIN `Location` `lo` ON (`sv`.`locationId` = `lo`.`id`)
                        WHERE `so`.`id` = " . $_GET['id'] . ";";
                $result = mysql_query($sql);
                $row = mysql_fetch_array($result);
                if ($result) {

                }
        ?>
    </div> <!-- /container -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="./js/vendor/jquery.min.js"><\/script>')</script>
    <script src="./js/bootstrap.min.js"></script>
</body>
