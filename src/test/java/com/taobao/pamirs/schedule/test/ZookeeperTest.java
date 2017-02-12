package com.taobao.pamirs.schedule.test;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

import com.taobao.pamirs.schedule.zk.ScheduleWatcher;
import com.taobao.pamirs.schedule.zk.ZKTools;

public class ZookeeperTest {
@Test
	 public void testCloseStatus() throws Exception{
		 ZooKeeper zk = new ZooKeeper("localhost:2181", 3000,
				 new  ScheduleWatcher(null));
		 int i = 1;
		 while(true){
			 try{
		 StringWriter writer = new StringWriter();
		 ZKTools.printTree(zk, "/zookeeper/quota", writer,"");
		 System.out.println(i++ +"----" +writer.getBuffer().toString());
		 Thread.sleep(2000);
			 }catch(Exception e){
				 System.out.println(e.getMessage());
			 }
		 }
	 }
 @Test
 public void testPrint() throws Exception{
	 ZooKeeper zk = new ZooKeeper("localhost:2181", 3000,
			 new  ScheduleWatcher(null));
	 StringWriter writer = new StringWriter();
	 ZKTools.printTree(zk, "/", writer,"\n");
	 System.out.println(writer.getBuffer().toString());
 }
 @Test
 public void deletePath() throws Exception{
	 ZooKeeper zk = new ZooKeeper("localhost:2181", 3000,
			 new  ScheduleWatcher(null));
	 zk.addAuthInfo("digest","ScheduleAdmin:password".getBytes());

	 ZKTools.deleteTree(zk,"/taobao-pamirs-schedule");
	 StringWriter writer = new StringWriter();
	 ZKTools.printTree(zk, "/", writer,"\n");
	 System.out.println(writer.getBuffer().toString());
 } 

	@Test
	public void testACL() throws Exception {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000,new  ScheduleWatcher(null));
		List<ACL> acls = new ArrayList<ACL>();
		zk.addAuthInfo("digest","TestUser:password".getBytes());
		acls.add(new ACL(ZooDefs.Perms.ALL,new Id("digest",DigestAuthenticationProvider.generateDigest("TestUser:password"))));
		acls.add(new ACL(ZooDefs.Perms.READ,Ids.ANYONE_ID_UNSAFE));
		zk.create("/abc", new byte[0], acls, CreateMode.PERSISTENT);
		zk.getData("/abc",false,null);
	}
}