<?php
namespace cross;
$dataFile = "php-ser.data";

class DummyClass {
    var $var1 = "đĐe";
    var $var2 = 123;
    var $var3 = 0.999;
    var $var4 = null;
    var $var5;
    function __construct(){
        $this->var5 = new \stdClass();
        $this->var5->x = [(float)123.4567, "k1" => "value1", "k2" => "Tiếng Việt"];
        $this->var6 = "a dynamic var";
    }
}

$data = [
    123456789012345,
    (double)123456789012345,
    123.456,
    (float)123.4567,
    "Tiếng Việt",
    ["Cường", "Đỗ", "Đức", "Gia", "Bảo"],
    [0 => "Cường", 1 => "Đỗ", 2 => "Đức", 3 => "Gia", 4 => "Bảo"],
    [123, "abc"],
    [0 => 123, 1 => "abc"],
    ["k1" => "value1", "k2" => "Tiếng Việt"],
    ["k1" => 123, "k2" =>
"aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ"
]
];
