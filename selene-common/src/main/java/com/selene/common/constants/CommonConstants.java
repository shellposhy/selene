package com.selene.common.constants;

import java.util.regex.Pattern;

/**
 * System basic common parameters
 * 
 * @author shaobo shih
 * @version 1.0
 */
public interface CommonConstants {

	String SELENE_LOGGER = "slf4j";

	String LOGIN_SESSION_USER = "login_session_user";

	String SELENE = "selene";

	String PROVIDER = "provider";

	String CONSUMER = "consumer";

	String APPLICATION_KEY = "application";

	String REMOTE_APPLICATION_KEY = "remote.application";

	String ENABLED_KEY = "enabled";

	String DISABLED_KEY = "disabled";

	String ANY_VALUE = "*";

	String COMMA_SEPARATOR = ",";

	String SPACE_SEPARATOR = " ";

	String EQUAL_SEPARATOR = "=";

	Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

	String PATH_SEPARATOR = "/";

	String PROTOCOL_SEPARATOR = "://";

	String REGISTRY_SEPARATOR = "|";

	Pattern REGISTRY_SPLIT_PATTERN = Pattern.compile("\\s*[|;]+\\s*");

	String SEMICOLON_SEPARATOR = ";";

	Pattern SEMICOLON_SPLIT_PATTERN = Pattern.compile("\\s*[;]+\\s*");

	String DEFAULT_PROXY = "javassist";

	String PROTOCOL_KEY = "protocol";

	int DEFAULT_CORE_THREADS = 0;

	int DEFAULT_THREADS = 200;

	String THREADPOOL_KEY = "threadpool";

	String THREAD_NAME_KEY = "threadname";

	String CORE_THREADS_KEY = "corethreads";

	String THREADS_KEY = "threads";

	String QUEUES_KEY = "queues";

	String ALIVE_KEY = "alive";

	String DEFAULT_THREADPOOL = "limited";

	String DEFAULT_CLIENT_THREADPOOL = "cached";

	String IO_THREADS_KEY = "iothreads";

	int DEFAULT_QUEUES = 0;

	int DEFAULT_ALIVE = 60 * 1000;

	String TIMEOUT_KEY = "timeout";

	int DEFAULT_TIMEOUT = 1000;

	long DEFAULT_TOKEN_TIMEOUT = 86400;// 1 day

	long DEFAULT_TOKEN_LONG_TIMEOUT = 604800;// 7 day

	long DEFAULT_SEARCH_TOKEN = 60 * 60;// 1 hour

	String REMOVE_VALUE_PREFIX = "-";

	String PROPERTIES_CHAR_SEPERATOR = "-";

	String GROUP_CHAR_SEPERATOR = ":";

	String HIDE_KEY_PREFIX = ".";

	String DEFAULT_KEY_PREFIX = "default.";

	String DEFAULT_KEY = "default";

	int DEFAULT_SERVER_SHUTDOWN_TIMEOUT = 10000;

	String SIDE_KEY = "side";

	String PROVIDER_SIDE = "provider";

	String CONSUMER_SIDE = "consumer";

	String ANYHOST_KEY = "anyhost";

	String ANYHOST_VALUE = "0.0.0.0";

	String LOCALHOST_KEY = "localhost";

	String LOCALHOST_VALUE = "127.0.0.1";

	String METHODS_KEY = "methods";

	String METHOD_KEY = "method";

	String PID_KEY = "pid";

	String TIMESTAMP_KEY = "timestamp";

	String GROUP_KEY = "group";

	String PATH_KEY = "path";

	String INTERFACE_KEY = "interface";

	String FILE_KEY = "file";

	String DUMP_DIRECTORY = "dump.directory";

	String CLASSIFIER_KEY = "classifier";

	String VERSION_KEY = "version";

	String REVISION_KEY = "revision";

	String RELEASE_KEY = "release";

	int MAX_PROXY_COUNT = 65535;

	String MONITOR_KEY = "monitor";

	String CLUSTER_KEY = "cluster";

	String USERNAME_KEY = "username";

	String PASSWORD_KEY = "password";

	String HOST_KEY = "host";

	String PORT_KEY = "port";

	int SUPER_ADMIN = 1;

	int RPC_DEFAULT_SIZE = 150;

	int MYSQL_TABLE_MAX_SIZE = 5000000;

	int DEFAULT_KEYWORDS_SIZE = 5;

	int DEFAULT_SUMMARY_LENGTH = 200;

	String INDEX_DATE_FORMAT = "yyyyMMddHHmmss";

	String SHOW_DATE_FORMAT = "yyyy年MM月dd日HH:mm:ss";

	String SIMPLE_DATE_FORMAT = "yyyyMMdd";

	String COMMON_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	int /* Limit image width */ DEFAULT_IMAGES_WIDTH = 800;

	int /* Limit image height */ DEFAULT_IMAGES_HEIGHT = 600;

	String SEARCH_INDEX_ALL = "*:*";

	int DEFAULT_SEARCH_INDEX_NUMBER_HITS = 1000000;

	String DEFAULT_REMOVE_CHAR = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	Pattern HTML_IMG_SRC = Pattern.compile(
			"<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
			Pattern.CASE_INSENSITIVE);

	Pattern HTML_LINK_HREF = Pattern.compile("<link\\b[^>]*\\bhref\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.css)\\b)[^>]*>",
			Pattern.CASE_INSENSITIVE);

	Pattern HTML_SCRIPT_SRC = Pattern.compile(
			"<script\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.js)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);

	Pattern HTML_FTL_SRC = Pattern.compile("<#include\\b\\s*('|\")?([^'\"\n\r\f>]+(\\.ftl)\\b)[^>]*>",
			Pattern.CASE_INSENSITIVE);

	Pattern HTML_ECODE_TEXT = Pattern.compile("<@.*?>(.*?)</@.*?>|<@.*?/*?>", Pattern.CASE_INSENSITIVE);

	Pattern HTML_ECODE_VALUE = Pattern.compile("\\s+(.*?)=\\S+|\\S+=(.*?)\\s", Pattern.CASE_INSENSITIVE);

	String DEFAULT_FREEMARKER_INDEX_HTML = "index.html";

	String DEFAULT_FREEMARKER_PUBLISH_PREFIX = "_publish";
}
