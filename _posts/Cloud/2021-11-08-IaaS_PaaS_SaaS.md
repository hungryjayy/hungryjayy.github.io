---

layout: post

title: IaaS, PaaS, SaaS

author: 
  name: hungryjayy
  link: https://github.com/hungryjayy

description: null

tags: [cloud, iaas, paas, saas]

featuredImage: 

img: 

categories: [Study, Cloud]

date: '2021-11-08'

extensions:

  preset: gfm


---

: 인프라, 플랫폼, 소프트 웨어의 형태로 클라우드 컴퓨팅을 제공하는 것

<br>

### 클라우드

: 기존 온프레미스 환경에서 기업 각각이 IT 인프라를 관리하고 책임졌다면, 클라우드 환경에서는 **더 중요한 사안(어떠한 서비스를 제공할 지)** 만 신경 쓰고, 인프라에 관한 것을 클라우드에 맡김

<br>

<img src="https://www.redhat.com/cms/managed-files/iaas-paas-saas-diagram5.1-1638x1046.png" alt="img" style="zoom:40%;" /> 



#### 각각이 지원해주는 정도와 비용이 위와 같이 다르기 때문에 적절한 클라우드를 비교하며 채택해 사용해야한다.

<br>

## IaaS

: Infrastructure as a Service. 서비스로서의 인프라

* 온프레미스 인프라와 대응되는, **온프레미스에서 클라우드화** 된 클라우드
* 스토리지, 네트워킹, 서버 등 **인스턴스**를 종량제로 이용할 수 있도록 제공
  * 자체 하드웨어 구매 비용을 절감할 수 있음
  * 데이터가 클라우드에 저장
* **데이터 센터를 보유하고 있지 않은 기업에 적합**
* 적절히 인프라를 구성할줄 알아야한다.
* 스케일링: 확장 또는 축소 할 수 있는 유연성 제공
* 주요 단점은 **보안** 문제, 여러 클라이언트와 인프라 리소스를 공유해야하는 **멀티 테넌트 시스템**
  * 인지도 있는 좋은 서비스를 이용해야한다.
* **AWS** EC2, **GCP** compute engine 등

<br>

## PaaS

: Platform as a Service. 서비스로서의 플랫폼

* IaaS에서 인프라 관리가 더 발전한 형태의 클라우드(약간 정의가 까다로울 수 있음)
* 사용자가 애플리케이션을 개발, 제공할 수 있는 **가상화 환경**을 제공
  * 사용자(개발자)는 **코드 작성, 빌드, 관리**에 집중할 수 있음
  * 인프라: 소프트웨어 업데이트나 하드웨어 관리는 제공업체에서 제공
  * 따라서 여러 사람이 같은 환경에서 개발, 테스팅 등 협업하는데 용이
* **민첩하게 개발해야하는 개발업체에 적합**
* **Heroku**, AWS 람다, 구글 앱 엔진

<br>

## SaaS

: Software as a Service. 서비스로서의 소프트웨어

* 인터넷으로 **클라우드 기반 소프트웨어 및 애플리케이션**을 제공
  * 사용자는 소프트웨어를 구독하고 웹 또는 공급업체 API를 통해 소프트웨어 이용
* 개별 시스템에 소프트웨어를 설치할 필요 없고 그냥 API 이용
* Gmail, ERP, 사무용 앱, Dropbox
  * 이와 같은 소프트웨어에 그냥 로그인해서 사용할 수 있다.

<br>

<br>

#### Reference)

https://www.ibm.com/kr-ko/cloud/learn/iaas-paas-saas

https://www.redhat.com/ko/topics/cloud-computing/iaas-vs-paas-vs-saas