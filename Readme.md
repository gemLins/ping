curl -XGET -H "username:admin" -H "password:123456" http://10.41.18.107:8082/api/v1/ping?ip=10.41.18.1/24


curl  -H "username:admin" -H "password:123456" 10.41.18.107:8082/api/v1/ping?ip=10.41.18.1/24
curl  -H "username:admin" -H "password:123456" localhost:8082/api/v1/ping?ip=10.245.86.62


curl -XGET -H "username:admin" -H "password:123456" http://10.41.18.107:8082/api/v1/ping?ip=10.245.86.22.1/24
//重复复制字符串

def repeatString(target: String,n: Integer): String = List.fill(n)(target).mkString(","")

import java.io._
val file = "/home/fzx/sj_work/ip.txt"
val writer=new PrintWriter(new File(file))
importPerson.foreach(writer.write)
writer.close()

val f = new File(file)
val fw = new FileWriter(f)
fw.write()

CHCP 65001