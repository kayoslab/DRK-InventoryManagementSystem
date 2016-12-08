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
