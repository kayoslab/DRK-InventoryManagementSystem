<?php
    include "connect.php";
    $sql = "SELECT `id`, `messageId` FROM `StockValue` WHERE `messageId` = 3;";
    $result = mysql_query($sql);

    if ($result && mysql_num_rows($result) > 0) {
        $sql = "SELECT `sv`.`id`, `so`.`title`, `sv`.`mtkDate`, `so`.`mtkIntervall`, `sv`.`stkDate`, `so`.`stkIntervall` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 3 and `so`.`typeId` = 1;";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            echo "<div class=\"panel panel-danger\"> \n";
            echo "<div class=\"panel-heading\"> \n";
            echo "<h3 class=\"panel-title\">Warnung(en) Geräte</h3> \n";
            echo "</div> \n";
            echo "<div class=\"panel-body\"> \n";
            echo "<p></p> \n";
            echo "</div> \n";
            echo "<table class=\"table table-striped\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
    		echo "<colgroup>\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "</colgroup>";
            echo "<tr><th>Titel</th> <th>Letzte MTK</th> <th>MTK Intervall</th> <th>Letzte STK</th> <th>STK Intervall</th></tr> \n";
            while ($stockValue = mysql_fetch_array($result)) {
                echo "<tr><td>" . $stockValue['title'] . "</td><td>" . date( 'm/d/y', strtotime($stockValue['mtkDate'])) . "</td><td>" . $stockValue['mtkIntervall'] . "</td><td>" . date( 'm/d/y', strtotime($stockValue['stkDate'])) . "</td><td> \n" . $stockValue['stkIntervall'] . "</td></tr>";
            }
            echo "</table> \n";
            echo "</div> \n";
        }

        $sql = "SELECT `sv`.`id`, `so`.`title`, `so`.`minimumStock`, `so`.`quotaStock`, `so`.`totalVolume`, `sv`.`date` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 3 and `so`.`typeId` = 2;";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            echo "<div class=\"panel panel-danger\"> \n";
            echo "<div class=\"panel-heading\"> \n";
            echo "<h3 class=\"panel-title\">Warnung(en) Medizinisches Material</h3> \n";
            echo "</div> \n";
            echo "<div class=\"panel-body\"> \n";
            echo "<p></p> \n";
            echo "</div> \n";
            echo "<table class=\"table table-striped\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
    		echo "<colgroup>\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "</colgroup>";
            echo "<tr><th>Titel</th> <th>Mindestbestand</th> <th>Sollbestand</th> <th>Lagerbestand</th> <th>Ablaufdatum</th></tr> \n";
            while ($stockValue = mysql_fetch_array($result)) {
                echo "<tr><td>" . $stockValue['title'] . "</td><td>" . $stockValue['minimumStock'] . "</td><td>" . $stockValue['quotaStock'] . "</td><td>" . $stockValue['totalVolume'] . "</td><td>" . date( 'm/d/y', strtotime($stockValue['date'])) . "</td></tr> \n";
            }
            echo "</table> \n";
            echo "</div> \n";
        }

        $sql = "SELECT `sv`.`id`, `so`.`title`, `so`.`minimumStock`, `so`.`quotaStock`, `so`.`totalVolume`, `sv`.`date` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 2 and `so`.`typeId` = 3;";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            echo "<div class=\"panel panel-danger\"> \n";
            echo "<div class=\"panel-heading\"> \n";
            echo "<h3 class=\"panel-title\">Warnung(en) Versorgungsmaterial</h3> \n";
            echo "</div> \n";
            echo "<div class=\"panel-body\"> \n";
            echo "<p></p> \n";
            echo "</div> \n";
            echo "<table class=\"table table-striped\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">";
    		echo "<colgroup>\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "<col width=\"20%\" />\n";
    		echo "</colgroup>";
            echo "<tr><th>Titel</th> <th>Mindestbestand</th> <th>Sollbestand</th> <th>Lagerbestand</th> <th>Ablaufdatum</th></tr> \n";
            while ($stockValue = mysql_fetch_array($result)) {
                echo "<tr><td>" . $stockValue['title'] . "</td><td>" . $stockValue['minimumStock'] . "</td><td>" . $stockValue['quotaStock'] . "</td><td>" . $stockValue['totalVolume'] . "</td><td>" . date( 'm/d/y', strtotime($stockValue['date'])) . "</td></tr> \n";
            }
            echo "</table> \n";
            echo "</div> \n";
        }
    }
?>
