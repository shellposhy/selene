package com.selene.viewing.admin.controller.dataing.databse;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.selene.common.HttpStatus;
import com.selene.common.result.ObjectResult;
import com.selene.viewing.admin.controller.BaseController;
import com.selene.viewing.admin.service.ResourceService;

@Controller
@RequestMapping("/admin/dataing/library/data/file")
public class DataingDataFileController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(DataingDataFileController.class.getName());
	@Resource
	private ResourceService resourceService;

	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	@ResponseBody
	public MappingJacksonValue delete(@RequestParam("id") Integer id, @RequestParam("uuid") String uuid,
			@RequestParam("fileName") String fileName, String callback) {
		ObjectResult<String> result = new ObjectResult<String>(HttpStatus.ERROR.code(), HttpStatus.ERROR.message());
		String rootPath = resourceService.realDoc();
		String realPath = rootPath + (rootPath.endsWith("/") ? "" : "/") + id + "/" + uuid;
		File file = new File(realPath + (realPath.endsWith("/") ? "" : "/") + fileName);
		if (file.exists() && file.isFile()) {
			if (FileUtils.deleteQuietly(file)) {
				result.setCode(HttpStatus.OK.code());
				result.setMsg(HttpStatus.OK.message());
			}
		}
		MappingJacksonValue mv = new MappingJacksonValue(result);
		mv.setJsonpFunction(callback);
		return mv;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public void upload(HttpServletRequest request, @RequestParam("uuid") String uuid) {
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest uploadRequest = resolver.resolveMultipart(request);
		MultipartFile multipartFile = uploadRequest.getFile("Filedata");
		LOG.info("original file name=" + multipartFile.getOriginalFilename());
		String targetPath = resourceService.tmpDoc(uuid);
		File targetFile = new File(targetPath.toString());
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		if (multipartFile.getSize() != 0) {
			String originalName = multipartFile.getOriginalFilename();
			String suffix = originalName.substring(originalName.lastIndexOf(".") + 1);
			Random random = new Random();
			String fileName = "" + random.nextInt(10000) + System.currentTimeMillis() + "." + suffix;
			File file = new File(targetPath + (targetPath.endsWith("/") ? "" : "/") + fileName);
			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				LOG.error("File name is [" + originalName + "] upload fail!", e);
			}
		}
	}
}
