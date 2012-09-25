package com.sohu.sur.spring;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;
import net.rubyeye.xmemcached.utils.XMemcachedClientFactoryBean;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.sohu.sur.cache.DayLimitCache;
import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.cache.PageCache;
import com.sohu.sur.cache.impl.DayLimitCacheEhcacheImpl;
import com.sohu.sur.cache.impl.DayLimitCacheMemcachedImpl;
import com.sohu.sur.cache.impl.ObjectCacheImpl;
import com.sohu.sur.cache.impl.PageCacheMemcachedImpl;
import com.sohu.sur.concurrent.LockManager;
import com.sohu.sur.concurrent.impl.LockManagerPoolImpl;
import com.sohu.sur.dao.AccountActivityDao;
import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.AccountVirtualDao;
import com.sohu.sur.dao.AccountVirtualLogDao;
import com.sohu.sur.dao.ActionDao;
import com.sohu.sur.dao.ActivityChangeLogDao;
import com.sohu.sur.dao.AllProductMedalStarsDao;
import com.sohu.sur.dao.BannedAppealDao;
import com.sohu.sur.dao.BonusChangeLogDao;
import com.sohu.sur.dao.GiftBagDao;
import com.sohu.sur.dao.MallLogDao;
import com.sohu.sur.dao.MedalDao;
import com.sohu.sur.dao.ProductDao;
import com.sohu.sur.dao.RankDao;
import com.sohu.sur.dao.ScoreFuncDao;
import com.sohu.sur.dao.ScoreLogDao;
import com.sohu.sur.dao.ScoreLotteryAnalyseDao;
import com.sohu.sur.dao.ScoreLotteryProductAnalyseDao;
import com.sohu.sur.dao.ScoreRoleDao;
import com.sohu.sur.dao.ScoreUserDao;
import com.sohu.sur.dao.SystemOverviewActionDao;
import com.sohu.sur.dao.SystemOverviewDao;
import com.sohu.sur.dao.SystemOverviewMedalDao;
import com.sohu.sur.dao.ToolbarDao;
import com.sohu.sur.dao.VirtualItemDao;
import com.sohu.sur.dao.VirtualOverviewDao;
import com.sohu.sur.dao.impl.AccountActivityDaoMorphiaImpl;
import com.sohu.sur.dao.impl.AccountDaoMorphiaImpl;
import com.sohu.sur.dao.impl.AccountVirtualDaoMorphiaImpl;
import com.sohu.sur.dao.impl.AccountVirtualLogDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ActionDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ActivityChangeLogDaoMorphiaImpl;
import com.sohu.sur.dao.impl.AllProductMedalStarsDaoMorphiaImpl;
import com.sohu.sur.dao.impl.BannedAppealDaoMorphiaImpl;
import com.sohu.sur.dao.impl.BonusChangeLogDaoMorphiaImpl;
import com.sohu.sur.dao.impl.GiftBagDaoMorphiaImpl;
import com.sohu.sur.dao.impl.MallLogDaoMorphiaImpl;
import com.sohu.sur.dao.impl.MedalDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ProductDaoMorphiaImpl;
import com.sohu.sur.dao.impl.RankDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreFuncDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreLogDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreLotteryAnalyseDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreLotteryProductAnalyseDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreRoleDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ScoreUserDaoMorphiaImpl;
import com.sohu.sur.dao.impl.SystemOverviewActionDaoMorphiaImpl;
import com.sohu.sur.dao.impl.SystemOverviewDaoMorphiaImpl;
import com.sohu.sur.dao.impl.SystemOverviewMedalDaoMorphiaImpl;
import com.sohu.sur.dao.impl.ToolbarDaoMorphiaImpl;
import com.sohu.sur.dao.impl.VirtualItemDaoMorphiaImpl;
import com.sohu.sur.dao.impl.VirtualOverviewDaoMorphiaImpl;
import com.sohu.sur.service.AccountService;
import com.sohu.sur.service.AccountVirtualLogService;
import com.sohu.sur.service.AccountVirtualService;
import com.sohu.sur.service.AllProductMedalStarsService;
import com.sohu.sur.service.AuthorityService;
import com.sohu.sur.service.BannedAppealService;
import com.sohu.sur.service.BonusChangeLogService;
import com.sohu.sur.service.ChangeActivityService;
import com.sohu.sur.service.ChangeBonusService;
import com.sohu.sur.service.FragService;
import com.sohu.sur.service.GiftBagService;
import com.sohu.sur.service.MallItemService;
import com.sohu.sur.service.MallLogService;
import com.sohu.sur.service.MedalService;
import com.sohu.sur.service.ProductService;
import com.sohu.sur.service.ScoreLogService;
import com.sohu.sur.service.ScoreLotteryAnalyseService;
import com.sohu.sur.service.ToolbarService;
import com.sohu.sur.service.UserService;
import com.sohu.sur.service.aspect.DayLimitCacheAspect;
import com.sohu.sur.service.aspect.MultDatastoreDaoAspect;
import com.sohu.sur.service.impl.AccountServiceImpl;
import com.sohu.sur.service.impl.AccountVirtualLogServiceImpl;
import com.sohu.sur.service.impl.AccountVirtualServiceImpl;
import com.sohu.sur.service.impl.AllProductMedalStarsServiceImpl;
import com.sohu.sur.service.impl.AuthorityServiceImpl;
import com.sohu.sur.service.impl.BannedAppealServiceImpl;
import com.sohu.sur.service.impl.BonusChangeLogServiceImpl;
import com.sohu.sur.service.impl.ChangeActivityServiceImpl;
import com.sohu.sur.service.impl.ChangeBonusServiceImpl;
import com.sohu.sur.service.impl.FragServiceRemoteImpl;
import com.sohu.sur.service.impl.GiftBagServiceImpl;
import com.sohu.sur.service.impl.MallItemServiceImpl;
import com.sohu.sur.service.impl.MallLogServiceImpl;
import com.sohu.sur.service.impl.MedalServiceImpl;
import com.sohu.sur.service.impl.ProductServiceImpl;
import com.sohu.sur.service.impl.ScoreLogServiceImpl;
import com.sohu.sur.service.impl.ScoreLotteryAnalyseServiceImpl;
import com.sohu.sur.service.impl.ToolbarServiceImpl;
import com.sohu.sur.service.impl.UserServiceImpl;
import com.sohu.sur.util.ConsistentHash;
import com.sohu.sur.util.MongoUtils;

/**
 * User: 郭勇 Date: 2011-3-14 16:13:25
 */
@Configuration
@ImportResource("classpath:/sur-domain-config.xml")
public class SurDomainConfig {

	private final static Logger logger = LoggerFactory
			.getLogger(SurDomainConfig.class);

	@Value("#{surAppProperties['sur.mongodb.host']}")
	private String dbHost;

	@Value("#{surAppProperties['sur.mongodb.port']}")
	private int dbPort;

	@Value("#{surAppProperties['sur.mongodb.name']}")
	private String dbName = "sur";

	@Value("#{surAppProperties['sur.mongodb.user']}")
	private String user;

	@Value("#{surAppProperties['sur.mongodb.password']}")
	private char[] password;

	@Value("#{surAppProperties['sur.mongodb.replicaSetSeeds']}")
	private String replicaSetSeeds;

	@Value("#{surAppProperties['sur.mongodb.slaveOk']}")
	private boolean slaveOk = false;

	@Value("#{surAppProperties['sur.mongodb.safe']}")
	private boolean safe = true;
	// hxw edit and add
	@Value("#{surAppProperties['sur.mongodb.autoConnectRetry']}")
	private String autoConnectRetry = "true";
	
	@Value("#{surAppProperties['sur.mongodb.maxWaitTime']}")
	private String maxWaitTime = "5000";
	
	@Value("#{surAppProperties['sur.mongodb.threadsAllowedToBlockForConnectionMultiplier']}")
	private String threadsAllowedToBlockForConnectionMultiplier = "5000";
	
	
	@Value("#{surAppProperties['sur.mongodb.connectTimeout']}")
	private String connectTimeout;
	
	@Value("#{surAppProperties['sur.mongodb.socketTimeout']}")
	private String socketTimeout;
	
	@Value("#{surAppProperties['sur.mongodb.connectionsPerHost']}")
	private String connectionsPerHost;

	@Value("#{surAppProperties['sur.mophia.mapping']}")
	private String mappingPackage = "com.sohu.sur.model";

	@Value("#{surAppProperties['sur.lockPool.size']}")
	private int lockPoolSize = 0;

	@Value("#{surAppProperties['sur.localCache.name']}")
	private String localCacheName = "dayLimit";

	@Value("#{surAppProperties['sur.localCache.inuse']}")
	private boolean useLocalCache = true;

	@Value("#{surAppProperties['sur.remoteCache.inuse']}")
	private boolean useRemoteCache = false;

	@Value("#{surAppProperties['sur.remoteCache.memcachedServers']}")
	private String memcachedServers = "localhost:11211";

	@Value("#{surAppProperties['sur.remoteCache.memcachedConnPoolSize']}")
	private int memcachedConnPoolSize = 100;

	@Value("#{surAppProperties['sur.api.changeBonus.uri']}")
	private String changeBonusUri = "/sur/change-bonus/{0}/";

	@Value("#{surAppProperties['sur.api.changeActivity.uri']}")
	private String changeActivityUri = "/sur/change-activity/{0}/{1}";

	@Value("#{surAppProperties['mall.itemGiftService.url']}")
	private String mallItemGiftServiceUrl = "";

	@Value("#{surAppProperties['mall.itemLotteryGiftService.url']}")
	private String mallItemLotteryGiftServiceUrl = "";

	@Deprecated
	@Value("#{surAppProperties['mall.logService.url']}")
	private String mallLogServiceUrl = "";

	@Value("#{surAppProperties['mall.recommend.url']}")
	private String mallRecommendUrl = "";

	@Bean(destroyMethod = "close")
	public Mongo mongo() throws UnknownHostException {

		ServerAddress address = new ServerAddress(dbHost, dbPort);

		MongoOptions options = new MongoOptions();
		options.safe = safe;
		options.connectionsPerHost = Integer.parseInt(connectionsPerHost);
		options.autoConnectRetry = Boolean.parseBoolean(autoConnectRetry);
		options.connectTimeout = Integer.parseInt(connectTimeout);
		options.socketTimeout = Integer.parseInt(socketTimeout);
		options.maxWaitTime = Integer.parseInt(maxWaitTime);
		options.threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(threadsAllowedToBlockForConnectionMultiplier);
		Mongo mongo;

		if (StringUtils.trimToNull(replicaSetSeeds) == null) {

			mongo = new Mongo(address, options);
			logger.info("replicaSetSeeds null");
		} else { // use replica set

			String[] hosts = StringUtils.split(replicaSetSeeds);
			List<ServerAddress> addr = new ArrayList<ServerAddress>();
			for (String host : hosts) {
				addr.add(new ServerAddress(host));
			}

			options.slaveOk = slaveOk;

			mongo = new Mongo(addr, options);
			logger.info("replicaSetSeeds ok");
		}

		logger.info("Mongo options = {}, wc = {}", options.toString(),
				options.getWriteConcern());

		return mongo;
	}

	

	@Bean
	public Map<String,Mongo> serverMongoMap() throws Exception {
		MongoOptions options = new MongoOptions();
		options.safe = safe;
		options.connectionsPerHost = Integer.parseInt(connectionsPerHost);
		options.autoConnectRetry = Boolean.parseBoolean(autoConnectRetry);
		options.connectTimeout = Integer.parseInt(connectTimeout);
		options.socketTimeout = Integer.parseInt(socketTimeout);
		options.maxWaitTime = Integer.parseInt(maxWaitTime);
		options.threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(threadsAllowedToBlockForConnectionMultiplier);
		String dbports="27010#27017";
		String dbHost="10.11.157.37#10.11.155.18";
		String replicaSetSeeds="10.11.157.37:27010#10.11.155.18:27017";
		Map<String,Mongo> serverMongoMap=MongoUtils.getMongoServerMap(dbHost, dbports, options, replicaSetSeeds, slaveOk);
		
		
		return serverMongoMap;
	}
	@Bean
	public MultDatastoreDaoAspect multDatastoreAspect(){
		return new MultDatastoreDaoAspect();
	}
	@Bean
	public Map<String,Datastore> serverDatastoreMap() throws Exception{
		DatastoreServerMapFactoryBean factoryBean = new DatastoreServerMapFactoryBean();
		factoryBean.setServerMongoMap(serverMongoMap());
		factoryBean.setDbName(dbName);
		factoryBean.setMappingPackage(mappingPackage);
		factoryBean.setUser(user);
		factoryBean.setPassword(password);
		return factoryBean.getObject();
	}
	@Bean
	public Datastore datastore() throws Exception {
		DatastoreFactoryBean factoryBean = new DatastoreFactoryBean();
		factoryBean.setMongo(mongo());
		factoryBean.setDbName(dbName);
		factoryBean.setMappingPackage(mappingPackage);
		factoryBean.setUser(user);
		factoryBean.setPassword(password);

		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}
	@Bean
   public ConsistentHash consistentHash() throws Exception{
	  
	   String[] servers={"10.11.157.37","10.11.155.18"};
	  
	   ConsistentHashFactoryBean factoryBean=new ConsistentHashFactoryBean();
		
		factoryBean.setServers(servers);
		factoryBean.afterPropertiesSet();
	   return factoryBean.getObject();
   }
	@Bean
	public BannedAppealDao bannedAppealDao() throws Exception {
		return new BannedAppealDaoMorphiaImpl(datastore());
	}
	
	@Bean
	public AccountActivityDao accountActivityDao() throws Exception {
		return new AccountActivityDaoMorphiaImpl(datastore());
	}

	@Bean
	public ActivityChangeLogDao activityChangeLogDao() throws Exception {
		return new ActivityChangeLogDaoMorphiaImpl(datastore());
	}

	@Bean
	public AccountDao accountDao() throws Exception {
		return new AccountDaoMorphiaImpl(datastore(),consistentHash(),serverDatastoreMap());
	}

	@Bean
	public ActionDao actionDao() throws Exception {
		return new ActionDaoMorphiaImpl(datastore());
	}
	
	
	@Bean
	public BonusChangeLogDao bonusChangeLogDao() throws Exception {
		return new BonusChangeLogDaoMorphiaImpl(datastore());
	}

	@Bean
	public AllProductMedalStarsDao allProductMedalStarsDao() throws Exception {
		return new AllProductMedalStarsDaoMorphiaImpl(datastore());
	}

	@Bean
	public MedalDao medalDao() throws Exception {
		return new MedalDaoMorphiaImpl(datastore());
	}

	@Bean
	public ProductDao productDao() throws Exception {
		return new ProductDaoMorphiaImpl(datastore());
	}

	@Bean
	public RankDao rankDao() throws Exception {
		return new RankDaoMorphiaImpl(datastore());
	}

	@Bean
	public MallLogDao mallLogDao() throws Exception {
		return new MallLogDaoMorphiaImpl(datastore());
	}

	@Bean
	public LockManager changeExpLockManager() {
		if (lockPoolSize == 0) {
			return new LockManagerPoolImpl();
		} else {
			return new LockManagerPoolImpl(lockPoolSize);
		}
	}

	@Bean
	public LockManager changeActivityLockManager() {
		if (lockPoolSize == 0) {
			return new LockManagerPoolImpl();
		} else {
			return new LockManagerPoolImpl(lockPoolSize);
		}
	}

	@Bean
	public HttpClient httpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 10000);
		ConnManagerParams.setMaxTotalConnections(params, 100);
		ConnManagerParams.setMaxConnectionsPerRoute(params,
				new ConnPerRouteBean(100));
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		HttpClient httpClient = new DefaultHttpClient(cm, params);
		return httpClient;
	}

	@Bean
	public CacheManager cacheManager() throws Exception {
		EhCacheManagerFactoryBean ehCacheManagerfactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerfactoryBean.afterPropertiesSet();
		CacheManager cacheManager = ehCacheManagerfactoryBean.getObject();
		return cacheManager;
	}

	@Bean
	public Ehcache dayLimitEhcache() throws Exception {
		EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
		factoryBean.setCacheManager(cacheManager());
		factoryBean.setCacheName(localCacheName);
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	@Bean
	public DayLimitCache dayLimitCacheLocal() throws Exception {

		return new DayLimitCacheEhcacheImpl(dayLimitEhcache());
	}

	@Bean
	public PageCache pageCache() throws Exception {
		return new PageCacheMemcachedImpl(memcachedClient());
	}

	@Bean
	public XMemcachedClient memcachedClient() throws Exception {
		XMemcachedClientFactoryBean factoryBean = new XMemcachedClientFactoryBean();
		factoryBean.setServers(memcachedServers);
		factoryBean.setConnectionPoolSize(memcachedConnPoolSize);
		SerializingTranscoder transcoder = new SerializingTranscoder();
		transcoder.setCharset("UTF-8");
		transcoder.setPackZeros(false);
		transcoder.setCompressionThreshold(1048576);// 1024KB
		factoryBean.setTranscoder(transcoder);
		factoryBean.setCommandFactory(new BinaryCommandFactory());
		factoryBean.setSessionLocator(new KetamaMemcachedSessionLocator());

		XMemcachedClient memcachedClient = (XMemcachedClient) factoryBean
				.getObject();
		return memcachedClient;
	}

	@Bean
	public DayLimitCacheAspect dayLimitCacheAspect() throws Exception {

		DayLimitCache dayLimitCacheRemote = null;

		if (useRemoteCache) {

			dayLimitCacheRemote = new DayLimitCacheMemcachedImpl(
					memcachedClient(), changeBonusUri, changeActivityUri);
		}

		return new DayLimitCacheAspect(dayLimitCacheLocal(),
				dayLimitCacheRemote, useLocalCache, useRemoteCache);
	}

	@Bean
	public BannedAppealService bannedAppealService() throws Exception {
		return new BannedAppealServiceImpl(bannedAppealDao(),accountDao());
	}
	
	@Bean
	public AccountService accountSummaryService() throws Exception {
		return new AccountServiceImpl(accountDao(), accountActivityDao(),
				medalDao(), rankDao(), productDao(), allProductMedalStarsDao(),
				mallItemService(),accountVirtualService(),objectCache());
	}

	@Bean
	public BonusChangeLogService bonusChangeLogService() throws Exception {
		return new BonusChangeLogServiceImpl(bonusChangeLogDao(), accountDao());
	}

	@Bean
	public ChangeActivityService changeActivityService() throws Exception {

		ChangeActivityService targetObject = new ChangeActivityServiceImpl(accountDao(), accountActivityDao(),
				changeActivityLockManager(), objectCache());

		AspectJProxyFactory factory = new AspectJProxyFactory(targetObject);

		factory.addAspect(dayLimitCacheAspect());

		return factory.getProxy();
	}

	@Bean
	public ChangeBonusService changeBonusService() throws Exception {

		ChangeBonusService targetObject = new ChangeBonusServiceImpl(
				accountDao(), actionDao(), bonusChangeLogDao(),
				changeExpLockManager(),objectCache());

		AspectJProxyFactory factory = new AspectJProxyFactory(targetObject);

		factory.addAspect(dayLimitCacheAspect());

		return factory.getProxy();
	}

	@Bean
	public MedalService medalService() throws Exception {
		return new MedalServiceImpl(medalDao(), productDao());
	}

	@Bean
	public ProductService productService() throws Exception {
		return new ProductServiceImpl(productDao(), medalDao(),
				accountActivityDao());
	}

	@Bean
	public AllProductMedalStarsService allProductMedalStarsService()
			throws Exception {
		return new AllProductMedalStarsServiceImpl(allProductMedalStarsDao());
	}

	@Bean
	public UserService userService() throws Exception {
		return new UserServiceImpl();
	}

	@Bean
	public MallItemService mallItemService() throws Exception {
		return new MallItemServiceImpl(httpClient(),
				this.mallItemGiftServiceUrl, this.mallItemLotteryGiftServiceUrl);
	}

	@Bean
	public FragService remoteFragService() throws Exception {
		return new FragServiceRemoteImpl(httpClient(), this.mallRecommendUrl);
	}

	@Bean
	MallLogService mallLogService() throws Exception {
		return new MallLogServiceImpl(mallLogDao());
	}
	//日志bean注册
	@Bean
	ScoreLogDao scoreLogDao() throws Exception {
		return new ScoreLogDaoMorphiaImpl(datastore());
	}
	@Bean
	ScoreLogService scoreLogService() throws Exception {
		return new ScoreLogServiceImpl(scoreLogDao());
	}
	//
	//抽奖商品分析dao
	@Bean
	ScoreLotteryProductAnalyseDao scoreLotteryProductAnalyseDao() throws Exception {
		return new ScoreLotteryProductAnalyseDaoMorphiaImpl(datastore());
	}
	//抽奖分析bean注册
	@Bean
	ScoreLotteryAnalyseDao scoreLotteryAnalyseDao() throws Exception {
		return new ScoreLotteryAnalyseDaoMorphiaImpl(datastore());
	}
	@Bean
	ScoreLotteryAnalyseService scoreLotteryAnalyseService() throws Exception {
		return new ScoreLotteryAnalyseServiceImpl(scoreLotteryAnalyseDao());
	}
	//
	//权限管理
	@Bean
	public ScoreUserDao scoreUserDao() throws Exception {
		return new ScoreUserDaoMorphiaImpl(datastore());
	}
	@Bean
	public ScoreRoleDao scoreRoleDao() throws Exception {
		return new ScoreRoleDaoMorphiaImpl(datastore());
	}
	@Bean
	public ScoreFuncDao scoreFuncDao() throws Exception {
		return new ScoreFuncDaoMorphiaImpl(datastore());
	}
	@Bean
	public AuthorityService authorityService() throws Exception {
		return new AuthorityServiceImpl(scoreUserDao(), scoreRoleDao(),scoreFuncDao());
	}
	//
	
	//金币系统概况分析
	@Bean
	public SystemOverviewDao systemOverviewDao() throws Exception {
		return new SystemOverviewDaoMorphiaImpl(datastore());
	}
	@Bean
	public SystemOverviewActionDao systemOverviewActionDao() throws Exception {
		return new SystemOverviewActionDaoMorphiaImpl(datastore());
	}
	@Bean
	public SystemOverviewMedalDao systemOverviewMedalDao() throws Exception {
		return new SystemOverviewMedalDaoMorphiaImpl(datastore());
	}
	//object memcache register
	@Bean
	public ObjectCache objectCache() throws Exception{
		return new ObjectCacheImpl(memcachedClient());
	}
	//toolbar
    @Bean
    public ToolbarDao toolbarDao() throws Exception {
        return new ToolbarDaoMorphiaImpl(datastore());
    }
    @Bean
    ToolbarService toolbarService() throws Exception {
        return new ToolbarServiceImpl(toolbarDao());
    }
    //调宝
    @Bean
    public GiftBagDao giftBagDao() throws Exception {
        return new GiftBagDaoMorphiaImpl(datastore());
    }
    @Bean
    GiftBagService giftBagService() throws Exception {
        return new GiftBagServiceImpl(giftBagDao());
    }
    //用户虚拟商品
    @Bean
    public AccountVirtualDao accountVirtualDao() throws Exception {
        return new AccountVirtualDaoMorphiaImpl(datastore());
    }
    @Bean
    AccountVirtualService accountVirtualService() throws Exception {
        return new AccountVirtualServiceImpl(accountVirtualDao(),accountVirtualLogDao());
    }
    //用户虚拟商品历史记录
    @Bean
    public AccountVirtualLogDao accountVirtualLogDao() throws Exception {
        return new AccountVirtualLogDaoMorphiaImpl(datastore());
    }
    @Bean
    AccountVirtualLogService accountVirtualLogService() throws Exception {
        return new AccountVirtualLogServiceImpl(accountVirtualLogDao());
    }
    //虚拟商品统计概要
    @Bean
    public VirtualOverviewDao virtualOverviewDao() throws Exception {
        return new VirtualOverviewDaoMorphiaImpl(datastore());
    }
    //单个虚拟商品统计
    @Bean
    public VirtualItemDao virtualItemDao() throws Exception {
        return new VirtualItemDaoMorphiaImpl(datastore());
    }
    public String getDbName() {
		return dbName;
	}
}
