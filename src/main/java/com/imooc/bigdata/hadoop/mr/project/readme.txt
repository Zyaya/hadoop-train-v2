统计页面的浏览量
    select count（1）from xxx;
    一行记录做成一个固定的KEY，value赋值为1

统计各个省份的浏览量
    select province, count(1) from xxx group by province;
    地市信息可以通过解析ip地址得到 <== ip 如何转换成城市信息
    ip解析：收费


统计页面的