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
    session_start();
    $server = 'localhost';
    $username = 'www';
    $password = 'SzGVb2mbCK2VMw8m';
    $database = 'stockManagement';
    if(!mysql_connect($server, $username, $password)) {
        exit('Error: could not establish database connection');
    }
    if(!mysql_select_db($database)){
        exit('Error: could not select the database');
    }
?>
