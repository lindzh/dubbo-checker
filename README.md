##dubbo-check
功能：
检测服务是否正常，或者正常部署
指定服务器和端口以及时间段检测服务是否正常
检测本机服务列表是否正常


###使用？
java -jar dubbo-checker-0.2.1.jar com.linda.dubbo.checker.CheckerCommandMain [options]

options命令说明：
-help打印帮助
-zk 192.168.139.129:2181 指定dubbo 注册中心地址 必须输入
-f dubbo.properties 指定dubbo配置文件为当前路径下的lin.properties 必须输入
-h 192.168.139.132 指定检测dubbo提供方地址 不加该参数默认 null 不检测
-p 4332 指定dubbo提供方端口 不加该参数默认0不检测
-t 600 指定dubbo注册服务时间与当前时间差 单位秒，不加该参数默认0不检测
-m n 指定检测模式 n 仅检测该配置文件中的服务有无部署，忽略host，port以及time diff
-m t 指定检测模式 t 通过host，port，time时间差检测
-m s 指定检测模式s，检测本机有无部署该服务，如果指定时间差t，则会检测部署时间是否在正常范围
 
说明：dubbo服务及列表请按照 lin.properties中格式，文件名称在命令行中指定

举例:
使用xml配合config.properties配置文件的方式
java -jar dubbo-checker-0.2.1.jar com.linda.dubbo.checker.CheckerFileMain -file config.properties
使用命令行方式检测
java -jar dubbo-checker-0.2.1.jar com.linda.dubbo.checker.CheckerCommandMain -zk 192.168.139.129:2181 -m t -t 3600 -f dubbo.properties
