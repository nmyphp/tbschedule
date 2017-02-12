package com.taobao.pamirs.schedule.taskmanager;

import java.util.List;
import java.util.Map;

import org.apache.zookeeper.data.Stat;

import com.taobao.pamirs.schedule.TaskItemDefine;


/**
 * 调度配置中心客户端接口，可以有基于数据库的实现，可以有基于ConfigServer的实现
 * 
 * @author xuannan
 * 
 */
public interface IScheduleDataManager{
	public long getSystemTime();
	/**
	 * 重新装载当前server需要处理的数据队列
	 * 
	 * @param taskType
	 *            任务类型
	 * @param uuid
	 *            当前server的UUID
	 * @return
	 * @throws Exception
	 */
	public List<TaskItemDefine> reloadDealTaskItem(String taskType,String uuid) throws Exception;

	/**
	 * 装载所有的任务队列信息
	 * @param taskType
	 * @return
	 * @throws Exception
	 */
	public List<ScheduleTaskItem> loadAllTaskItem(String taskType) throws Exception;
	
	/**
	 * 释放自己把持，别人申请的队列
	 * 
	 * @param taskType
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public void releaseDealTaskItem(String taskType,String uuid) throws Exception;

	/**
	 * 获取一共任务类型的处理队列数量
	 * 
	 * @param taskType
	 * @return
	 * @throws Exception
	 */
	public int queryTaskItemCount(String taskType) throws Exception;

	/**
	 * 装载任务类型相关信息
	 * 
	 * @param taskType
	 * @throws Exception
	 */
	public ScheduleTaskType loadTaskTypeBaseInfo(String taskType) throws Exception;
	
	/**
	 * 清除已经过期的调度服务器信息
	 * 
	 * @param taskInfo
	 * @throws Exception
	 */
	public int clearExpireScheduleServer(String taskType,long expireTime) throws Exception;

	/**
	 * 清除任务信息，服务器已经不存在的时候
	 * 
	 * @param taskInfo
	 * @throws Exception
	 */
	public int clearTaskItem(String taskType, List<String> serverList) throws Exception;

	/**
	 * 获取所有的有效服务器信息
	 * 
	 * @param taskInfo
	 * @return
	 * @throws Exception
	 */
	public List<ScheduleServer> selectAllValidScheduleServer(String taskType) throws Exception;
	public List<String> loadScheduleServerNames(String taskType)throws Exception;
	/**
	 * 重新分配任务Item
	 * 
	 * @param taskType
	 * @param serverList
	 * @throws Exception
	 */
	public void assignTaskItem(String taskType,String currentUuid, int maxNumOfOneServer,List<String> serverList) throws Exception;

	/**
	 * 发送心跳信息
	 * 
	 * @param server
	 * @throws Exception
	 */
	public boolean refreshScheduleServer(ScheduleServer server) throws Exception;

	/**
	 * 注册服务器
	 * 
	 * @param server
	 * @throws Exception
	 */
	public void registerScheduleServer(ScheduleServer server) throws Exception;

	/**
	 * 注销服务器
	 * @param serverUUID
	 * @throws Exception
	 */
	public void unRegisterScheduleServer(String taskType,String serverUUID) throws Exception;
	/**
	 * 清除已经过期的OWN_SIGN的自动生成的数据
	 * @param taskType 任务类型
	 * @param serverUUID 服务器
	 * @param expireDateInternal 过期时间，以天为单位
	 * @throws Exception
	 */
	public void clearExpireTaskTypeRunningInfo(String baseTaskType,String serverUUID,double expireDateInternal)throws Exception;
	
	public boolean isLeader(String uuid,List<String> serverList);
	
	public void pauseAllServer(String baseTaskType)throws Exception;
	public void resumeAllServer(String baseTaskType)throws Exception;

	public List<ScheduleTaskType> getAllTaskTypeBaseInfo()throws Exception ;
	
	/**
	 * 清除一个任务类型的运行期信息
	 * @param baseTaskType
	 * @throws Exception
	 */
	public void clearTaskType(String baseTaskType) throws Exception;
	/**
	 *  创建一个新的任务类型
	 * @param baseTaskType
	 * @throws Exception
	 */
    public void createBaseTaskType(ScheduleTaskType baseTaskType) throws Exception ;
    public void updateBaseTaskType(ScheduleTaskType baseTaskType) throws Exception ;
    public List<ScheduleTaskTypeRunningInfo> getAllTaskTypeRunningInfo(String baseTaskType) throws Exception;
    
    /**
     * 删除一个任务类型
     * @param baseTaskType
     * @throws Exception
     */
    public void deleteTaskType(String baseTaskType) throws Exception;
	
	/**
	 * 根据条件查询当前调度服务
	 * @param baseTaskType
	 * @param ownSign
	 * @param ip
	 * @param orderStr
	 * @return
	 * @throws Exception
	 */
	public List<ScheduleServer> selectScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
			throws Exception;

	/**
	 * 查询调度服务的历史记录
	 * @param baseTaskType
	 * @param ownSign
	 * @param ip
	 * @param orderStr
	 * @return
	 * @throws Exception
	 */
	public List<ScheduleServer> selectHistoryScheduleServer(String baseTaskType, String ownSign, String ip, String orderStr)
			throws Exception;

	public List<ScheduleServer> selectScheduleServerByManagerFactoryUUID(String factoryUUID) throws Exception;

	/**
	 * 创建任务项。注意其中的 CurrentSever和RequestServer不会起作用
	 * @param taskItems
	 * @throws Exception
	 */
	public void createScheduleTaskItem(ScheduleTaskItem[] taskItems) throws Exception;
	
	/**
	 * 更新任务的状态和处理信息
	 * @param taskType
	 * @param sts
	 * @param message
	 */
	public void updateScheduleTaskItemStatus(String taskType,String taskItem,ScheduleTaskItem.TaskItemSts sts,String message)throws Exception;

	/**
	 * 删除任务项
	 * @param taskType
	 * @param taskItem
	 */
	public void deleteScheduleTaskItem(String taskType,String taskItem) throws Exception;
	/**
	 * 初始化任务调度的域信息和静态任务信息
	 * @param baseTaskType
	 * @param ownSign
	 * @param serverUUID
	 * @throws Exception
	 */
	public void initialRunningInfo4Static(String baseTaskType, String ownSign,String uuid)throws Exception;
	public void initialRunningInfo4Dynamic(String baseTaskType, String ownSign)throws Exception;
	/**
	 * 运行期信息是否初始化成功
	 * @param baseTaskType
	 * @param ownSign
	 * @param serverUUID
	 * @return
	 * @throws Exception
	 */
	public boolean isInitialRunningInfoSucuss(String baseTaskType, String ownSign) throws Exception;
	public void setInitialRunningInfoSucuss(String baseTaskType, String taskType,String uuid) throws Exception;
	public String getLeader(List<String> serverList);
	
	public long updateReloadTaskItemFlag(String taskType) throws Exception;
	public long getReloadTaskItemFlag(String taskType) throws Exception;
	
	/**
	 * 通过taskType获取当前运行的serverList信息。
	 * @param taskType
	 * @return
	 * @throws Exception
	 */
	public Map<String ,Stat> getCurrentServerStatList(String taskType) throws Exception;
	 
}
