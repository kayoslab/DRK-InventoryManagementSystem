<?php
    /*
     * Copyright (c) - All Rights Reserved
     *
     * Unauthorized copying of these files, via any medium is
     * strictly prohibited Proprietary and confidential
     *
     * NOTICE:
     * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
     * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
     * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
     * material is strictly forbidden unless prior written permission is obtained by the owner.
     *
     * @author
     *
     */
    include "connect.php";
    $sql = "SELECT `id`, `messageId` FROM `StockValue` WHERE `messageId` = 2;";
    $result = mysql_query($sql);

    if ($result && mysql_num_rows($result) > 0) {
        $sql = "SELECT * FROM `UserIsMemberOfGroup` WHERE (`group` = 2 OR `group` = 3) AND `user` = " . $_SESSION['userId'] . ";";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            $sql = "SELECT `sv`.`id`, `so`.`id` as `object`, `so`.`title`, `sv`.`mtkDate`, `so`.`mtkIntervall`, `sv`.`stkDate`, `so`.`stkIntervall` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 2 and `so`.`typeId` = 1;";
            $result = mysql_query($sql);
            if ($result && mysql_num_rows($result) > 0) {
                echo "<div class=\"panel panel-warning\"> \n";
                echo "<div class=\"panel-heading\"> \n";
                echo "<h3 class=\"panel-title\">Meldung(en) Geräte</h3> \n";
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
                    $mtkDateString = "";
                    $stkDateString = "";
                    if (!is_null($stockValue['mtkDate'])) {
                        $mtkDateString = date( 'd.m.y', strtotime($stockValue['mtkDate']));
                    }
                    if (!is_null($stockValue['stkDate'])) {
                        $stkDateString = date( 'd.m.y', strtotime($stockValue['stkDate']));
                    }
                    echo "<tr>".
                    "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>".
                    "<td>" . $mtkDateString . "</td>".
                    "<td>" . $stockValue['mtkIntervall'] . " Monate</td>".
                    "<td>" . $stkDateString . "</td>".
                    "<td>" . $stockValue['stkIntervall'] . " Monate</td>".
                    "</tr> \n";
                }
                echo "</table> \n";
                echo "</div> \n";
            }
        }

        $sql = "SELECT * FROM `UserIsMemberOfGroup` WHERE (`group` = 2 OR `group` = 4) AND `user` = " . $_SESSION['userId'] . ";";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            $sql = "SELECT `sv`.`id`, `so`.`id` as `object`, `so`.`title`, `sv`.`minimumStock`, `sv`.`quotaStock`, `so`.`totalVolume`, `sv`.`date` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 2 and `so`.`typeId` = 2;";
            $result = mysql_query($sql);
            if ($result && mysql_num_rows($result) > 0) {
                echo "<div class=\"panel panel-warning\"> \n";
                echo "<div class=\"panel-heading\"> \n";
                echo "<h3 class=\"panel-title\">Meldung(en) Medizinisches Material</h3> \n";
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
                    $dateString = "";
                    if (!is_null($stockValue['date'])) {
                        $dateString = date( 'd.m.y', strtotime($stockValue['date']));
                    }
                    echo "<tr>".
                    "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>".
                    "<td>" . $stockValue['minimumStock'] . "</td>".
                    "<td>" . $stockValue['quotaStock'] . "</td>".
                    "<td>" . $stockValue['totalVolume'] . "</td>".
                    "<td>" . $dateString . "</td>".
                    "</tr> \n";
                }
                echo "</table> \n";
                echo "</div> \n";
            }
        }

        $sql = "SELECT * FROM `UserIsMemberOfGroup` WHERE (`group` = 2 OR `group` = 5) AND `user` = " . $_SESSION['userId'] . ";";
        $result = mysql_query($sql);
        if ($result && mysql_num_rows($result) > 0) {
            $sql = "SELECT `sv`.`id`, `so`.`id` as `object`, `so`.`title`, `sv`.`minimumStock`, `sv`.`quotaStock`, `so`.`totalVolume`, `sv`.`date` FROM `StockValue` `sv` INNER JOIN `StockObject` `so` ON (`sv`.`stockObjectId` = `so`.`id`) WHERE `sv`.`messageId` = 2 and `so`.`typeId` = 3;";
            $result = mysql_query($sql);
            if ($result && mysql_num_rows($result) > 0) {
                echo "<div class=\"panel panel-warning\"> \n";
                echo "<div class=\"panel-heading\"> \n";
                echo "<h3 class=\"panel-title\">Meldung(en) Versorgungsmaterial</h3> \n";
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
                    $dateString = "";
                    if (!is_null($stockValue['date'])) {
                        $dateString = date( 'd.m.y', strtotime($stockValue['date']));
                    }
                    echo "<tr>".
                    "<td><a href=\"./object.php?id=" . $stockValue['object'] . "\">" . utf8_encode($stockValue['title'])  . "</a></td>".
                    "<td>" . $stockValue['minimumStock'] . "</td>".
                    "<td>" . $stockValue['quotaStock'] . "</td>".
                    "<td>" . $stockValue['totalVolume'] . "</td>".
                    "<td>" . $dateString . "</td>".
                    "</tr> \n";
                }
                echo "</table> \n";
                echo "</div> \n";
            }
        }
    }
?>
