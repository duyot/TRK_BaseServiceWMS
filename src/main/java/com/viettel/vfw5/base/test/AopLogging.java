package com.viettel.vfw5.base.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by vtsoft on 1/9/2015.
 */
@Component
@Aspect
public class AopLogging {

    private final Log log = LogFactory.getLog(this.getClass());
//    @Around("execution(* com.viettel.sale.repo.*.*(..))")

    public Object logTimeMethod(ProceedingJoinPoint jp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
//        StringBuilder logMessage = new StringBuilder();
        long startTime = System.currentTimeMillis();
        try {
            // Proceed with method invocation
            System.out.println("before  " + startTime + " ; " + jp.getTarget().getClass().getName() + ":" + jp.getSignature().getName());

//            if (checkResource(20, 200, 50, 1.00, 1) == 0) {
//                System.out.println("NOT ENOUGH RESOUCES");
//                //return null;
//                //redirectPage();
//            } else {
//                System.out.println("ENOUGH RESOURCES");
//            }
            Object retVal = jp.proceed();
            stopWatch.stop();

            // append args
//            StringBuffer sb = new StringBuffer();
//            Object[] args = jp.getArgs();
//            for (int i = 0; i < args.length; i++) {
//                sb.append(args[i]).append(",");
//            }
//            if (args.length > 0) {
//                logMessage.deleteCharAt(logMessage.length() - 1);
//            }
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            System.out.println("affter " + time + " ; " + jp.getTarget().getClass().getName() + ":" + jp.getSignature().getName());
            //Goi ham gc sau moi lan chay
           // System.gc();
            return retVal;
        } catch (Throwable e) {
            // Log error
            e.printStackTrace();
            log.error(e.getMessage(), e);
//            throw e;
            return null;
        }
//        return null;

    }
//
//    public void redirectPage() {
//
//        //checkResource(long MIN_freeMemory (MB), long MIN_FreePhysicalMemory (MB), long MAX_ActiveThread, double MAX_SystemCpuLoad - present for the percentage, 0.5 means that 50%)       
//        //if don't need to check a type of resource, set the corresponding parameter by -1 
//        //if (checkResource(20, 200, 50, 1.00, 1) == 0)
//        {
//            ExternalContext extenalContext = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletRequest request = (HttpServletRequest) extenalContext.getRequest();
//            HttpServletResponse response = (HttpServletResponse) extenalContext.getResponse();
//            try {
//                //response.sendRedirect(request.getContextPath() + "/Warning.htm");
//                response.sendError(1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    /*About checkResouce
     Return: 1 - enough resource, 0 - else
     Parameters:
     MIN_X <=> X needs to be less than this parameter
     MAX_X <=> X needs to be greater than this parameter            
     */
    public int checkResource(long MIN_freeMemory, long MIN_FreePhysicalMemory, long MAX_ActiveThread, double MAX_SystemCpuLoad,
            int MAX_CurrentSessionCount) {
        long MB = 1024 * 1024;
//
//        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
//        IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
//        IStatsProducer sessionCountProducer = registry.getProducer("SessionCount");
//
//        //SessionCountStats sessionCountStats = (SessionCountStats) sessionCountProducer.getStats().get(0);
//        System.out.println("Runtime.getRuntime().freeMemory() = " + Runtime.getRuntime().freeMemory() / MB + " MB");
//        System.out.println("operatingSystemMXBean.getFreePhysicalMemorySize() = " + ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getFreePhysicalMemorySize() / MB + " MB");
//        System.out.println("Thread.activeCount() = " + Thread.activeCount());
//        System.out.println("operatingSystemMXBean.getSystemCpuLoad() = " + ((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getSystemCpuLoad());
//        //System.out.println("sessionCountStats.getCurrentSessionCount(null) = " + sessionCountStats.getCurrentSessionCount(null));
//
//        if (MIN_freeMemory > -1) {
//            if (Runtime.getRuntime().freeMemory() / MB < MIN_freeMemory) {
//                return 0;
//            }
//        }
//
//        if (MIN_FreePhysicalMemory > -1) {
//            if (((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getFreePhysicalMemorySize() / MB < MIN_FreePhysicalMemory) {
//                return 0;
//            }
//        }
//
//        if (MAX_ActiveThread > -1) {
//            if (Thread.activeCount() > MAX_ActiveThread) {
//                return 0;
//            }
//        }
//
//        if (MAX_SystemCpuLoad > -1) {
//            if (((com.sun.management.OperatingSystemMXBean) operatingSystemMXBean).getSystemCpuLoad() > MAX_SystemCpuLoad) {
//                return 0;
//            }
//        }

        /*
         if (MAX_CurrentSessionCount > -1)
         {            
         if (sessionCountStats.getCurrentSessionCount(null) >  MAX_CurrentSessionCount)
         return 0;
         }
         */
        return 1;
    }

//    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("/com/viettel/fw/test/applicationContext.xml");
//        TestObject testObject = ctx.getBean(TestObject.class);
//        testObject.setObject(new BpmParameter("test",null, "test"));
////        testObject.throwExceptionAndCatch();
//
//    }
}
