//package com.example.task.mail;
//
//@Component
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class SyncAccountStatusWorker {
//
//    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//    ExecutorService executorService = Executors.newSingleThreadExecutor();
//    AccountServiceClient accountServiceClient;
//
//    public SyncAccountStatusWorker(AccountServiceClient accountServiceClient) {
//        this.accountServiceClient = accountServiceClient;
//        scheduledExecutorService.scheduleAtFixedRate(this::job, 0, 1, TimeUnit.HOURS);
//    }
//
//    public void job() {
//        try {
//            log.info("Start sync account status");
//            var profileData = accountServiceClient.getProfileWaitingSyncStatus(1, 100).getData();
//            while (!profileData.isEmpty()) {
//                for (var profile : profileData.getData()) {
//                    executorService.submit(syncAccountStatus(profile));
//                }
//                profileData = accountServiceClient.getProfileWaitingSyncStatus(profileData.getPage() + 1, profileData.getSize()).getData();
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    private Runnable syncAccountStatus(Profile profile) {
//        return () -> {
//            try {
//                Thread.sleep(300);
//                log.info("Sync {}", profile);
//                accountServiceClient.syncAccountStatus(profile.getUserId());
//            } catch (Exception e) {
//                log.debug(e.getMessage());
//            }
//        };
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        scheduledExecutorService.shutdown();
//        executorService.shutdown();
//    }
//
//}