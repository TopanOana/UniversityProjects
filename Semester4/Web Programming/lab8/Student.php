<?php
class Student implements JsonSerializable {
    private $id;
    private $name;
    private $username;
    private $password;
    private $groupID;

    public function __construct($id, $name, $groupID, $username, $password) {
        $this->id = $id;
        $this->name = $name;
        $this->password = $password;
        $this->username = $username;
        $this->groupID = $groupID;
    }

    public function getId() {
        return $this->id;
    }
    public function getName() {
        return $this->name;
    }
    public function getPassword() {
        return $this->password;
    }
    public function getGroupID() {
        return $this->groupID;
    }

    public function getUsername(){
        return $this->username;
    }

    public function jsonSerialize() {
        $vars = get_object_vars($this);
        return $vars;
    }
}

?>