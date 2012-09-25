package com.sohu.sur.model;

import com.google.code.morphia.Datastore;
import com.sohu.sur.spring.SurDomainConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

/**
 * User: guoyong
 * Date: 11-3-24 下午4:05
 */
public class InitData {

    private ApplicationContext ctx;

    private Datastore ds;

    public InitData() {

        ctx = new AnnotationConfigApplicationContext(SurDomainConfig.class);

        ds = ctx.getBean(Datastore.class);


    }

    public void clean() {
        ds.delete(ds.find(Product.class));
        ds.delete(ds.find(Action.class));
        ds.delete(ds.find(Medal.class));
        ds.delete(ds.find(Rank.class));
    }

    public void initAction() {

        Action view = new Action();
        view.setName("浏览新闻");
        view.setCode(RandomStringUtils.randomAlphanumeric(3));
        view.setDescription("");
        view.setCreateTime(new Date());
        view.setEnabled(true);
        view.setValidationCode(RandomStringUtils.randomAlphanumeric(6));

        view.setExpCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        view.setExpDefaultValue(1);
        view.setExpTargetValue(0);

        view.setBonusCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        view.setBonusDefaultValue(1);
        view.setBonusTargetValue(0);
        view.setBonusChangeType(BonusChangeType.REWARD.getValue());

        view.setMaxExpPerDay(50);

        ds.save(view);

        Action redeem = new Action();
        redeem.setName("积分兑换");
        redeem.setCode(RandomStringUtils.randomAlphanumeric(3));
        redeem.setDescription("");
        redeem.setCreateTime(new Date());
        redeem.setEnabled(true);
        redeem.setValidationCode(RandomStringUtils.randomAlphanumeric(6));

        redeem.setExpCalcMethod(BonusCalculationMethod.NOCHANGE.getValue());
        redeem.setExpDefaultValue(0);
        redeem.setExpTargetValue(0);

        redeem.setBonusCalcMethod(BonusCalculationMethod.INPUT.getValue());
        redeem.setBonusDefaultValue(0);
        redeem.setBonusTargetValue(0);
        redeem.setBonusChangeType(BonusChangeType.PAYMENT.getValue());

        redeem.setMaxExpPerDay(Integer.MAX_VALUE);

        ds.save(redeem);

        Action register = new Action();
        register.setName("新用户注册");
        register.setCode(RandomStringUtils.randomAlphanumeric(3));
        register.setDescription("");
        register.setCreateTime(new Date());
        register.setEnabled(true);
        register.setValidationCode(RandomStringUtils.randomAlphanumeric(6));

        register.setExpCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        register.setExpDefaultValue(100);
        register.setExpTargetValue(0);

        register.setBonusCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        register.setBonusDefaultValue(100);
        register.setBonusTargetValue(0);
        register.setBonusChangeType(BonusChangeType.PAYMENT.getValue());

        register.setMaxExpPerDay(-1);

        ds.save(register);

        Action logon = new Action();
        logon.setName("每日登录");
        logon.setCode(RandomStringUtils.randomAlphanumeric(3));
        logon.setDescription("");
        logon.setCreateTime(new Date());
        logon.setEnabled(true);
        logon.setValidationCode(RandomStringUtils.randomAlphanumeric(6));

        logon.setExpCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        logon.setExpDefaultValue(10);
        logon.setExpTargetValue(0);

        logon.setBonusCalcMethod(BonusCalculationMethod.DEFAULT.getValue());
        logon.setBonusDefaultValue(10);
        logon.setBonusTargetValue(0);
        logon.setBonusChangeType(BonusChangeType.PAYMENT.getValue());

        logon.setMaxExpPerDay(10);

        ds.save(logon);
    }

    /**
     * 频道数据 勋章数据
     */
    public void initProductAndMedals() {

        Product news = new Product();
        news.setName("新闻");
        news.setDescription("搜狐新闻频道");
        news.setCode(RandomStringUtils.randomAlphanumeric(3));
        news.setShowOrder(100);
        news.setEnabled(true);
        news.setValidationCode(RandomStringUtils.randomAlphanumeric(6));
        news.setCreateTime(new Date());

        ds.save(news);

        Medal newsBronze = new Medal();
        newsBronze.setName("铜章");
        newsBronze.setDescription("");
        newsBronze.setForProduct(news.getId());
        newsBronze.setProductName(news.getName());
        newsBronze.setCode(RandomStringUtils.randomAlphanumeric(3));
        newsBronze.setCreateTime(new Date());
        newsBronze.setDiscount(0.95);
        newsBronze.setMinActiveDays(4);
        newsBronze.setMaxActiveDays(7);

        Medal newsSilver = new Medal();
        newsSilver.setName("银章");
        newsSilver.setDescription("");
        newsSilver.setForProduct(news.getId());
        newsSilver.setProductName(news.getName());
        newsSilver.setCode(RandomStringUtils.randomAlphanumeric(3));
        newsSilver.setCreateTime(new Date());
        newsSilver.setDiscount(0.90);
        newsSilver.setMinActiveDays(8);
        newsSilver.setMaxActiveDays(30);

        Medal newsGold = new Medal();
        newsGold.setName("金章");
        newsGold.setDescription("");
        newsGold.setForProduct(news.getId());
        newsGold.setProductName(news.getName());
        newsGold.setCode(RandomStringUtils.randomAlphanumeric(3));
        newsGold.setCreateTime(new Date());
        newsGold.setDiscount(0.85);
        newsGold.setMinActiveDays(31);
        newsGold.setMaxActiveDays(90);

        Medal newsDiamond = new Medal();
        newsDiamond.setName("金章");
        newsDiamond.setDescription("");
        newsDiamond.setForProduct(news.getId());
        newsDiamond.setProductName(news.getName());
        newsDiamond.setCode(RandomStringUtils.randomAlphanumeric(3));
        newsDiamond.setCreateTime(new Date());
        newsDiamond.setDiscount(0.80);
        newsDiamond.setMinActiveDays(91);
        newsDiamond.setMaxActiveDays(Integer.MAX_VALUE);

        ds.save(newsBronze);
        ds.save(newsSilver);
        ds.save(newsGold);
        ds.save(newsDiamond);

    }



    /**
     * 等级数据
     */
    public void initRank() {

        Rank rank0 = new Rank();
        rank0.setName("新新小狐");
        rank0.setIconId("rank0");
        rank0.setMinQualifiedExp(0);
        rank0.setMaxQualifiedExp(49);
        rank0.setCreateTime(new Date());

        Rank rank1 = new Rank();
        rank1.setName("狐宝宝");
        rank1.setIconId("rank1");
        rank1.setMinQualifiedExp(50);
        rank1.setMaxQualifiedExp(99);
        rank1.setCreateTime(new Date());

        Rank rank2 = new Rank();
        rank2.setName("狐武仆");
        rank2.setIconId("rank2");
        rank2.setMinQualifiedExp(100);
        rank2.setMaxQualifiedExp(199);
        rank2.setCreateTime(new Date());

        Rank rank3 = new Rank();
        rank3.setName("狐武者");
        rank3.setIconId("rank3");
        rank3.setMinQualifiedExp(200);
        rank3.setMaxQualifiedExp(399);
        rank3.setCreateTime(new Date());

        Rank rank4 = new Rank();
        rank4.setName("狐武士");
        rank4.setIconId("rank4");
        rank4.setMinQualifiedExp(400);
        rank4.setMaxQualifiedExp(799);
        rank4.setCreateTime(new Date());

        Rank rank5 = new Rank();
        rank5.setName("狐武师");
        rank5.setIconId("rank5");
        rank5.setMinQualifiedExp(800);
        rank5.setMaxQualifiedExp(1599);
        rank5.setCreateTime(new Date());

        Rank rank6 = new Rank();
        rank6.setName("狐武宗");
        rank6.setIconId("rank6");
        rank6.setMinQualifiedExp(1600);
        rank6.setMaxQualifiedExp(3199);
        rank6.setCreateTime(new Date());

        Rank rank7 = new Rank();
        rank7.setName("狐武王");
        rank7.setIconId("rank7");
        rank7.setMinQualifiedExp(3200);
        rank7.setMaxQualifiedExp(5599);
        rank7.setCreateTime(new Date());

        Rank rank8 = new Rank();
        rank8.setName("狐武圣");
        rank8.setIconId("rank8");
        rank8.setMinQualifiedExp(5600);
        rank8.setMaxQualifiedExp(8799);
        rank8.setCreateTime(new Date());

        Rank rank9 = new Rank();
        rank9.setName("狐武神");
        rank9.setIconId("rank9");
        rank9.setMinQualifiedExp(8800);
        rank9.setMaxQualifiedExp(11999);
        rank9.setCreateTime(new Date());

        Rank rank10 = new Rank();
        rank10.setName("狐灵仙");
        rank10.setIconId("rank1");
        rank10.setMinQualifiedExp(12000);
        rank10.setMaxQualifiedExp(15999);
        rank10.setCreateTime(new Date());

        Rank rank11 = new Rank();
        rank11.setName("狐玉仙");
        rank11.setIconId("rank11");
        rank11.setMinQualifiedExp(16000);
        rank11.setMaxQualifiedExp(19999);
        rank11.setCreateTime(new Date());

        Rank rank12 = new Rank();
        rank12.setName("狐地仙");
        rank12.setIconId("rank12");
        rank12.setMinQualifiedExp(20000);
        rank12.setMaxQualifiedExp(24999);
        rank12.setCreateTime(new Date());

        Rank rank13 = new Rank();
        rank13.setName("狐天仙");
        rank13.setIconId("rank13");
        rank13.setMinQualifiedExp(25000);
        rank13.setMaxQualifiedExp(29999);
        rank13.setCreateTime(new Date());

        Rank rank14 = new Rank();
        rank14.setName("狐真仙");
        rank14.setIconId("rank14");
        rank14.setMinQualifiedExp(30000);
        rank14.setMaxQualifiedExp(35999);
        rank14.setCreateTime(new Date());

        Rank rank15 = new Rank();
        rank15.setName("狐玄仙");
        rank15.setIconId("rank15");
        rank15.setMinQualifiedExp(36000);
        rank15.setMaxQualifiedExp(49999);
        rank15.setCreateTime(new Date());

        Rank rank16 = new Rank();
        rank16.setName("狐金仙");
        rank16.setIconId("rank16");
        rank16.setMinQualifiedExp(50000);
        rank16.setMaxQualifiedExp(64999);
        rank16.setCreateTime(new Date());

        Rank rank17 = new Rank();
        rank17.setName("狐仙君");
        rank17.setIconId("rank17");
        rank17.setMinQualifiedExp(65000);
        rank17.setMaxQualifiedExp(79999);
        rank17.setCreateTime(new Date());

        Rank rank18 = new Rank();
        rank18.setName("狐仙");
        rank18.setIconId("rank18");
        rank18.setMinQualifiedExp(80000);
        rank18.setMaxQualifiedExp(99999);
        rank18.setCreateTime(new Date());

        Rank rank19 = new Rank();
        rank19.setName("狐准圣");
        rank19.setIconId("rank19");
        rank19.setMinQualifiedExp(100000);
        rank19.setMaxQualifiedExp(149999);
        rank19.setCreateTime(new Date());

        Rank rank20 = new Rank();
        rank20.setName("狐圣人");
        rank20.setIconId("rank20");
        rank20.setMinQualifiedExp(150000);
        rank20.setMaxQualifiedExp(Integer.MAX_VALUE);
        rank20.setCreateTime(new Date());

        ds.save(rank0);
        ds.save(rank1);
        ds.save(rank2);
        ds.save(rank3);
        ds.save(rank4);
        ds.save(rank5);
        ds.save(rank6);
        ds.save(rank7);
        ds.save(rank8);
        ds.save(rank9);
        ds.save(rank10);
        ds.save(rank11);
        ds.save(rank12);
        ds.save(rank13);
        ds.save(rank14);
        ds.save(rank15);
        ds.save(rank16);
        ds.save(rank17);
        ds.save(rank18);
        ds.save(rank19);
        ds.save(rank20);
    }

    public static void main(String[] args) {

        InitData initData = new InitData();
        initData.clean();
        initData.initProductAndMedals();
        initData.initRank();
        initData.initAction();
    }
}
