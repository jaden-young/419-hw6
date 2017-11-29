APP = friendcount
MAIN_CLASS = me.jadenyoung.hw6.FriendCount
HDFS_INDIR = hdfs:/user/$(USER)/$(APP)/input
HDFS_OUTDIR = hdfs:/user/$(USER)/$(APP)/output
LOCAL_INDIR = input
LOCAL_OUTDIR = output
MKDIR_P = mkdir -p
HDFS_MKDIR_P = hdfs dfs -mkdir -p
HDFS_CP_FROM_LOCAL = hdfs dfs -copyFromLocal -f
HDFS_CP_TO_LOCAL = hdfs dfs -copyToLocal
HDFS_RM_R = hdfs dfs -rm -r -f
NUM_REDUCERS = 2
SRCDIR = src/main/java/me/jadenyoung/hw6

.PHONY: clean hdfs run turnin

$(APP).jar:
	gradle jar

hdfs:
	$(HDFS_MKDIR_P) $(HDFS_INDIR) && \
		$(HDFS_CP_FROM_LOCAL) $(LOCAL_INDIR)/* $(HDFS_INDIR)/ && \
		$(HDFS_RM_R) $(HDFS_OUTDIR)

run: $(APP).jar hdfs
	hadoop jar $(APP).jar $(MAIN_CLASS) -input $(HDFS_INDIR) -output $(HDFS_OUTDIR)  -numReducers $(NUM_REDUCERS) && \
		$(RM) -r $(LOCAL_OUTDIR) && \
		$(MKDIR_P) $(LOCAL_OUTDIR) && \
		$(HDFS_CP_TO_LOCAL) $(HDFS_OUTDIR) .

clean:
	$(RM) *.jar
	$(RM) $(BUILDDIR)/*
	$(RM) $(LOCAL_OUTDIR)/*
	hdfs dfs -rm -r -f $(HDFS_INDIR)
	hdfs dfs -rm -r -f $(HDFS_OUTDIR)

turnin:
	$(RM) -r turnin && \
	$(MKDIR_P) turnin && \
	rsync -a $(SRCDIR)/ turnin/ && \
	cp ./build/libs/friendcount.jar turnin/ && \
	cp README turnin/ && \
	cp Makefile turnin/ && \
	cd turnin && \
	tar czf jadyoungAssig6.tgz *
