No	Task
1	A Highly available architecture that resists to the failure of single component
-	using multi component with ELB, and EC2 split to EC2 + RDS
2	Scaling to meet the demand, but with uncertainty around when and how much this demand will be they are very concerned about buying too much resource too soon
-	Scale-up and Scale-out issue, Calculate expected traffic and needed size and scale
3	Disaster Recovery should be considered in case of multiple components failure
-	Backup and restore policy and solution needed each components
4	Their ability to configure their database and data access layer for high performance and throughput
-	split RDS role with read and write
5	Making the user experience in the browser very low latency even though a large portion of their user base will be from far away
-	Consider cache and CDN
6	Effective distribution of load regardless whether it’s http/1.1 or http/2 request
-	Application ELB
7	A self-healing infrastructure that recovers from failed service instances
-	consider AWS OpsWorks
8	Security of data at rest and in transit
-	Amazon Glacier, Amazon Simple Storage Service(S3) w/ AWS Storage Gateway
9	Securing access to the environment as the delivery team expands
-	IAM and Authorization
10	An archival strategy for inactive objects greater than 6 months
-	Archivie
11	Ability to easily manage and replicate multiple environments based on their blueprint architecture.
-	Blueprint
12	Application lifecycle management should be considered as a DevOps strategy
-	AWS DevOps
13	Cost-effectiveness should also be considered across all components of the architecture
-	Cost-Effectiveness
14	Access logs generated need to be collected and aggregated for visualization
-	Amazon CloudWatch

1.	CloudWatch를 넣은 이유는? 임계치를 CloudWatch로 볼 수 있는가?
-> CloudWatch와 CloudTrail을 같이 사용하여 오토스케일링 그룹을 보도록 사용하는 것이 중요함
→ CloudWatch를 사용하면, 클라우드워치로 EC2 인스턴스 상태를 감시하고, 감시 항목이 지정한 임계치를 넘으면 경보를 발생하게 되고, 알람을 받으면 EC2 인스턴스를 기동시키게 됩니다.
→ CloudTrail을 사용하면, Amazon EC2 Auto Scaling 용 이벤트를 비롯하여 AWS 계정에 지속적인 이벤트 기록을 보유할 수 있다. 즉, 이벤트 기록 확인, 검색 및 다운로드 가능
2.	Master/Slave 구조 중 Slave로 둔 것은 read데이터만 해당되는가?
→ 그렇다. 위의 구조에서 Master는 CUD작업을 처리하고, Slave는 R작업만 처리가 가능하다. Slave는 Read Replica이기 때문이다.
4.	Master와 Slave간의 통신은 어떻게 하는가? Master Slave 실시간 데이터는 어떻게 저장되나? (바로저장되는건 아닌데) 
→ Master와 Slave는 서로 dedicated storage를 사용하고 async 방식으로 데이터를 복제
5.	Redis와 Mater의 연관성이 없음 (write에 대한 내용이 없음)
→ 애플리케이션은 데이터베이스의 데이터를 읽어야 하는 경우에 먼저 캐시에 쿼리한다. 데이터를 찾을 수 없는 경우에 애플리케이션은 데이터베이스에 쿼리하여 그 결과를 캐시에 채운다.
5.	WEB 옆에 Auto Scaling 왜 따로 배치했나?
✅ 오토스케일링 그룹과 웹을 따로놓으면 오토스케일링이 되지 않는다.(각자 따로 처리하게 된다)
→ Auto Scaling이 되면 생기는 것을 나타내기 위해서 WEB 옆에 Auto Scaling Group을 그렸습니다. 하지만잘 못되었다는 것을 알게 되었고, 앞으로는 하나로 표시하도록 하겠습니다.
6.	글로벌 확장계획? Arch Flow가 없음
→ 현재는 단일 리전이지만, 리전을 더 만들어서 CloudFront를 이용해 글로벌 확장할 계획을 생각했습니다. 다이어그램에 빠졌지만, 앞으로는 다이어그램에 포함시키도록 하겠습니다.
7.	Bastion으로 접속 가능한 범위 명시해주기(web용인건지, was용인건지, web/was 겸용인건지..)Bation을 통해 어디까지 접속할수 있나? (web용..was용 등등..)
→ 위의 아키텍처 다이어그램에서는 WAS용으로 사용.
8.	webric을 사용한 이유? (shield는 무료로 방어해줌(폭넓게 생각)
→ AWS Shield, AWS WAF와 비교했을 때, 더 다양한 기능을 저렴하게 제공하기 때문. 또한, 타사 서비스인 Incapsula, Cloudflare에 비해 저렴하다.
📌 기능 : 웹 취약점 점검, 웹 공격 차단, 웹 공격 현황 확인, DDoS 방어, SSL(Secure Socket Layer는 웹사이트 방문자와 웹 서버간의 통신을 암호화하기 위하여 웹서버에 저장해야하는 인증서) 인증서 제공, URL제어, 전문 웹 보안 컨설팅, 네트워크 현황 분석(네트워크 트래픽을 일별/월별로..), 월간 리포트, IP제어 및 국가 접근 제어
📌 요금 : Basic Plan 27,000원/월(Mail support), Advanced Plan 89,100원/월(Phone support) 부가세 제외
9.	DB엔진은 어떤걸 사용했는가? 왜 mysql을 사용했는가?
✅ 현재 구조에서는 오로라가 더 나았을 것이다
→ Aurora 성능은 벤치마크 도구인 시스벤치(SysBench)에서는 RDS for MySQL에 비해 최대 5배, 온라인 트랜잭션 벤치 마크 도구인 TPC-C에서는 성능이 약 2.5배로 측정되었다. 오로라는 로그 구성 저장소라는 추가 확장이 자유로운 저장소 시스템을 채택하고 있다. 이 것은 마치 로그 파일처럼 끝부분에 연속해서 업데이트 데이터를 저장해나갈 수 있다.
→ 일반적인 MySQL은 업데이트 시에 갱신되는 행에 대하여 잠금이 발생하며 참조 시에도 읽기에 대한 일관성을 보장하기 위해 잠금이 발생한다. 이로 인해 동시에 많은 트랜잭션이 병행하여 실행되면 처리량이 저하된다. 오로라의 로그 구조화된 저장소는 이러한 잠금으로 인한 대기가 잘 발생되지 않아 MySQL보다 빠른 속도로 데이터를 읽고 쓸 수 있다.
10.	ELB(ALB/NLB)를 쓴이유?
→ ELB는 실행 중인 모든 EC2 인스턴스에 들어오는 애플리케이션 트래픽을 자동으로 분산하는 데 사용된다. Elastic Load Balancing을 사용하면 단 하나의 인스턴스에도 부하가 걸리지 않도록 트래픽 라우팅을 최적화하여 들어오는 요청을 관리할 수 있다.
→ 기본적으로 Port를 이용하여 로드 밸런싱을 하며, 상태 검사에 성공한 EC2만이 요청 전달 대상이 된다. 상태 검사 방법에는 HTTP, HTTPS, TCP등이 있다. ALB와 NLB는 대상그룹(Target Group)을 지정하며, CLB는 로드밸런서에 직접 등록하게 된다.
ALB:  L7 로드밸런서로, HTTP/HTTPS Header정보를 기반으로 요청을 전달하는 로드밸런서. client ip를 x-forwarded-for 헤더로 전달, URL Path 단위 등 ALB Rule을 통해 로드밸런싱 가능, AWS WAF 사용 가능
NLB: L4 로드밸런서로, TCP/UDP를 기반으로 요청을 전달하는 로드밸런서. client ip를 변경하지 않고 서버에 그대로 전달 /NLB의 IP가 ALB와 다르게 변경되지 않고 고정됨. 고정IP 할당 가능
11.	Public Subnet에 Web Server를 만든이유?
→ Web Server에는 애플리케이션 계층으로 클라이언트 부분이라고 생각해서 Public Subnet에 만들었습니다.
12.	Private Subnet/Public Subnet의 차이는?
✅ Public Subnet
- 퍼블릭 인터넷에 대한 직접 액세스를 지원
- 인터넷 게이트웨이에 대한 라우팅 테이블 항목에 의해 지정됨
✅ Private Subnet
- 인터넷 게이트웨이에 대한 라우팅 테이블 항목이 없음
- 퍼블릭 서브넷에 있는 NAT(Network Address Translation) 게이트웨이를 사용해서 퍼블릭 인터넷에 간접 액세스 가능
** 소프트웨어 업데이트시 NAT 게이트웨이를 사용하여 데이터베이스 서버를 인터넷에 연결할 수 있지만, 인터넷에서 데이터베이스 서버 연결을 설정할 수 없음.
13.	Bastion Host의 역할(털리면 다털리는거 아닌가?)
→ 방화벽 소프트웨어의 일종으로서 내부, 외부 네트워크 사이에 게이트웨이 역할을 하는 host / 외부에서 접근 가능하도록 public ip부여
** 베스천 호스트의 보안상 취약점은 로그인관련 정보 유출시 내부 네트워크의 방어가 불가능하며 데이터링크 계층을 통한 우회공격에 취약하다는 단점이다.
14.	VPC에 대한 보안 컨설팅과 AWS에 대한 보안 컨설팅?
위의 아키텍처 다이어그램에서
→ VPC에 대한 보안 컨설팅으로는 Bastion Host, Private Subnet, Security Group이 있습니다. 이 외에는 NACL이 있습니다.
→ AWS에 대한 보안 컨설팅에는 WEBBRICK, CloudFront(HTTPS 연결 구성, 특정 지리적 위치의 사용자가 콘텐츠에 액세스하지 목하도록 차단 가능), VPC가 있습니다. 이 외에는 AWS Shield, AWS WAF, Amazon GuardDuty가 있습니다.
15.	S3에는 뭐가 들어갔나?
→ 웹에서 사용되는 img, video가 저장될 것입니다.
16.	CloudFront랑 직접적으로 뭐랑 연결됨?
→ ALB가 직접적으로 연결됩니다. 위의 다이어그램은 트래픽 흐름을 그린것으로 CloudFront와 IG가 연결되어 있지만, ALB와 직접적으로 연결되어 있습니다.

This architecture suggests using Amazon AWS cloud computing products and services to meet the current and future rapid growth needs of startups. AWS's innovative business model liberates start-ups, allows them to expand quickly, allows products to go public faster, while keeping costs under control and keeping the company small
Overview
The customer's current LAMP architecture, built on desktop PC, must be considered in terms of manageability, security, scalability, high performance, flexibility, high availability, disaster recovery, etc. In order to meet the needs of the fast development of future business.
Cost is often an important factor in an early architecture that is simple, flexible, and efficient to meet recurring changes in business and demand. The EC2 flexible cloud computing server, using AWS, buys computing power based on business needs and maximizes investment.
In the high-speed business development phase, server requirements also increased, AWS's extensible architecture by automatically adding computing resources to meet the business peak requirements. After the peak of the business, you can release extra computing resources.
AWS allows customers to focus on business design, development, without the time-consuming and laborious consideration of server selection, procurement, deployment, network, firewall, security issues, and even the maintenance, maintenance, monitoring and so on of IT equipment
Assumptions
To simplify complexity, the scheme is based on the following assumptions and conditions :
The typical server structure is divided into three tiers : Web Server, App Server, Database Server. If the PHP program is only running initially, it can be modified to two layers, Web Server and App Server merging into one layer.
There is no massive data query and concurrent access, you can use NOSQL, such as DynamoDB, if necessary.
Data warehouse computing requirements are not considered, and if available, products such as RedShift, EMR can be selected.
There are no long running tasks, and products such as SQS message queues, SNS notification services can be used if needed.
Architecture
According to customer needs and future development needs, the architecture designed for it is shown in the following figure :
The AWS services used in this architecture are :
First, Amazon VPC isolates internal resources from external networks. Create public subnets for Web Server of accessible Internet, and place backend systems such as Database Server, AppServer in private subnets that cannot access Internet.
Multiple security layers such as Security Groups and network access control list are used to help control access to Amazon EC2 instances in each subnet.
External AWS Route 53 services are provided for clients visited by web. For employees, Direct Connect, VPN, IAM authentication visits AWS, CloudTrail can be used for security audits.
CloudFront distributes CDN content, caches common static, dynamic files, realizes low latency, high speed data transmission.
Elastic Load Balancer (abbreviation ELB) automatically allocates application access traffic between multiple Amazon EC2 instances in the cloud. It allows you to achieve a higher level of application fault tolerance by distributing business requests to multiple Availability Zone (AZ) Web Server.
EC2 provides computing resources for Web Server, App Server, DB Server. EC2 provides an adjustable computing capacity.
Different application servers can be customized for their own AMI mirror, the implementation of the rapid installation and deployment of the server.
Auto Scaling implement EC2 auto scaling. A Auto Scaling group can contain one or more instances of a EC2 available area EC2 from the same region.
With Amazon CloudWatch, you can get a comprehensive view of resource utilization, application performance, and performance across the system. With these analysis results, you can react in a timely manner to keep your application running smoothly.
Elastic Block Store (abbreviation EBS) provides data persistence storage for EC2.
The security policy designed by Security Groups ensures permissible network access.
Using ElastiCache as a cache can provide faster Web application responses.
MySQL databases use RDS services (MySQL or Aurora) and data files are stored on EBS. The database is deployed to different AZ, performing master, slave replication, achieving high availability and read and write separation.
EBS volume Snapshot, AMI backup, can be stored on S3. S3 also stores static data and objects.
Backups of databases, historical data, and archived data are done using Glacier.
Create templates using CloudFormation to facilitate deployment of AWS resources and applications.
AWS OpsWorks applications manage services that facilitate the deployment and operation of applications of different shapes and sizes, such as PHP applications.
Issues addressed
AWS has the following support for customer concerns :
Scaling to meet the demand
Auto Scaling group on the web layer and app layer can expand and contract on demand, respectively. Auto Scaling is suitable for applications with stable requirements as well as applications with fluctuating usage per hour, day, and week. Use Auto Scaling to simply add a new EC2 instance. Database extensions can be manually partitioned, partitioned, or migrated to DynamoDB for automatic extensions.
Their lack of provision for Disaster Recovery
In the AWS architecture above, there are two available AZ, and the database has a master copy across AZ to ensure that when an AZ problem occurs, ELB will distribute access requests, which are automated, have no perception of the end user, and ELB can reroute the user to recover AZ after failure AZ recovery.
Using EBS stored data volume, you can also ensure data security on EBS when EC2 fails.
When RDS switches to slave, ElastiCache can connect to slave to continue service.
Their ability to configure their database and data access layer for high performance and throughput
EC2 can always adjust the configuration to meet database performance requirements. RDS configuration of the main, from replication, read and write separation can be achieved, sharing the database access pressure. The front end also has ElastiCache as a cache, which also reduces the need to read the database. These all improve database performance and throughput.
Making the user experience in the browser browser very browser very low latency even though a large portion of their user base will be from far away
AWS has a global region that meets the needs of nearby visitors. AWS Route 53 can route user requests to the nearest server. Equipped with CloudFront and S3 can provide low latency access to static data. Web Server equipped with high IOPS performance EBS can also improve response speed.
Effective distribution of load
The Web Server front-end ELB can equalize user requests to a different Web Server. There is also load balancing at the App Server front end to assign application requests to different App Server.
Most static content access is undertaken by CloudFront, reducing the pressure on the web layer, the app layer, and the DB layer.
A self-healing infrastructure that recovers from failed service failed
ELB checks the health of EC2 instances and removes or moves them in. When CloudWatch finds a problem with instance, auto Scaling adds a new instance to the rule to eliminate the impact of resource shortages. Across AZ deployed RDS, the database can automatically failover go slave to instance.
Security of data at rest and in transit
Each EC2's Security Groups can have different access policies, such as port 80 / 443 for the server and port 3306 for the database host. VPC isolates internal resources from external networks. Set IAM for network access control and authorization. Data can be transferred using SSL encryption.
securing access to the environment as the delivery team expands 
All internal staff visits require IAM authorization CloudTrail for security audits. In VPC, you can set App Server and DB Server for Intranet access, secure isolation and access control, and improve security.
An archival strategy for inactive objects greater than 6 months 
Glacier can archive static data and historical data in the database according to the rules set in S3, and backup inactive data for more than six months.
ability to easily manage and replicate multiple environments based on their blueprint architecture 
AWS OpsWorks can define the application architecture and specifications for each component (including resources such as package installation, software configuration, and storage). With CloudFormation, existing AWS resources can be defined as templates, and by customizing or modifying templates, multiple environments can be easily replicated for different purposes.

In summary, using AWS scalable products and services can help startups build manageable, secure, high availability, scalable, and affordable IT architectures that allow customers to meet their immediate and future needs.

• Scaling to meet the demand, but with uncertainty around when and how much this demand will be they are very concerned about buying too much resource too soon
Auto Scaling group on the web layer and app layer can expand and contract on demand, respectively. Auto Scaling is suitable for applications with stable requirements as well as applications with fluctuating usage per hour, day, and week. Use Auto Scaling to simply add a new EC2 instance. Database extensions can be manually partitioned, partitioned, or migrated to DynamoDB for automatic extensions
• Disaster Recovery should be considered in case of multiple components failure
In the AWS architecture above, there are two available AZ, and the database has a master copy across AZ to ensure that when an AZ problem occurs, ELB will distribute access requests, which are automated, have no perception of the end user, and ELB can reroute the user to recover AZ after failure AZ recovery. Using EBS stored data volume, you can also ensure data security on EBS when EC2 fails. When RDS switches to slave, ElastiCache can connect to slave to continue service.
• Their ability to configure their database and data access layer for high performance and throughput
EC2 can always adjust the configuration to meet database performance requirements. RDS configuration of the main, from replication, read and write separation can be achieved, sharing the database access pressure. The front end also has ElastiCache as a cache, which also reduces the need to read the database. These all improve database performance and throughput
• Making the user experience in the browser very low latency even though a large portion of their user base will be from far away
AWS has a global region that meets the needs of nearby visitors. AWS Route 53 can route user requests to the nearest server. Equipped with CloudFront and S3 can provide low latency access to static data. Web Server equipped with high IOPS performance EBS can also improve response speed
• A self-healing infrastructure that recovers from failed service instances
ELB checks the health of EC2 instances and removes or moves them in. When CloudWatch finds a problem with instance, auto Scaling adds a new instance to the rule to eliminate the impact of resource shortages. Across AZ deployed RDS, the database can automatically failover go slave to instance.
• Security measures for all service layers need to be considered
• An archival strategy for inactive objects greater than 6 months
Glacier can archive static data and historical data in the database according to the rules set in S3, and backup inactive data for more than six months
• Ability to easily manage and replicate multiple environments based on their blueprint architecture.
AWS OpsWorks can define the application architecture and specifications for each component (including resources such as package installation, software configuration, and storage). With CloudFormation, existing AWS resources can be defined as templates, and by customizing or modifying templates, multiple environments can be easily replicated for different purposes.
• Cost-effectiveness should also be considered across all components of the architecture

1. Amazon Route 53 provides DNS configuration.
2. AWS WAF is web application firewall that protects system against common web exploits.
3. Amazon CloudFront is a fast content delivery network (CDN) that speeds up distribution of static and dynamic web content.
4. First Elastic Load Balancing (Application Load Balancer) distributes traffic across Varnish instances in an AWS Auto Scaling group in multiple Availability Zones.
5. Varnish Cache is a web application accelerator caching HTTP reverse proxy. The Enterprise version, available via AWS marketplace, is recommended as it has better features to support cloud backends and cache-purge across dynamic hosts
6. Second Elastic Load Balancing (Application Load Balancer) distributes traffic from Varnish Cache across the AWS Auto Scaling group of Magento instances in multiple Availability Zones. Install the latest version of Magento open source or Magento Commerce edition on Amazon EC2 instances.
7. Installation consists of Magento application, Nginx webserver and PHP. Build the Amazon Machine Image (AMI) to launch new instances in Auto Scaling group. 
8. Amazon Elasticsearch Service is managed elastic search service for Magento catalog search.
9. Amazon ElastiCache for Redis provides a caching layer for database.
10. Use Amazon Aurora or Amazon RDS to simplify database administration (including high availability and multi-master configuration).
11.  EFS mount targets facilitate mapping the Amazon Elastic File System (Amazon EFS) to Varnish and Magento instances.
12. Use Amazon EFS to access shared config across Varnish and shared media assets across Magento
instances.
13. Use Amazon Personalize extension for Magento to export users historical data into an Amazon S3 bucket. 
14. Based on the historical data exported from Magento, Amazon Personalize is trained to create a custom solution and campaign. The result is a private ML model hosted in your AWS account. 
15. Once Amazon Personalize model is activated, the model displays product recommendations to end users and adds real-time data interaction indicators, allowing Amazon Personalize to learn from users (add a product to their cart or wish list).

