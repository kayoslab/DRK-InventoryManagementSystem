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
 
<?php
   ob_start();
   session_start();
?>
<html lang="en">
<!DOCTYPE html>
    <?php
        include 'head.php';
        $msg = '';
        if (isset($_POST['submit']) && !empty($_POST['username']) && !empty($_POST['password'])) {
            include 'connect.php';
            $sql = "SELECT `id`, `username`, `password` FROM `User` WHERE `username` = '" . $_POST['username'] . "';";
            $result = mysql_query($sql);
            if ($result) {
                // while I have more results, loop through them
                // returning each result as an array
                while ($user = mysql_fetch_array($result)) {
                    if (md5($_POST['password']) === $user['password']) {
                        $_SESSION['valid'] = true;
                        $_SESSION['timeout'] = time();
                        $_SESSION['userId'] = $user['id'];
                        break;
                    }
                }
                if(isset($_SESSION['valid']) == FALSE ) {
                    $msg = 'Username oder Password fehlerhaft.';
                }
            }
        }
        if(isset($_SESSION['valid']) == FALSE ) {
            include 'login.php';
        } else {
            include 'news.php';
        }
    ?>
</html>
