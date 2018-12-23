package com.thtf.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.thtf.bean.WttsEntity;

public class Test {
	private static final String SOLR_URL = "http://localhost:8983/solr/";// "http://localhost:8888/solr";
	private static final String CORE_NAME = "bxyfconf";// "new_core";

	public static void main(String[] args) throws IOException, SolrServerException {
		HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();
		SolrQuery query = new SolrQuery(); // 设置要查询的字段
//		query.setFields("id", "name", "age", "hobby"); // 查询全部
		query.setFields("csrw");
		QueryResponse response = client.query(CORE_NAME, query); // 查询结果
		SolrDocumentList results = response.getResults();
		System.out.println("结果总数：" + results.getNumFound()); // 遍历列表
		System.out.println("文档结果：");
		for (SolrDocument doc : results) {
			System.out.printf("[id:%s，name:%s，age:%s，hobby:%s]\n", doc.get("id"), doc.get("name"), doc.get("age"),
					doc.get("hobby"));
		}
		// 得到实体对象
		List<WttsEntity> userList = response.getBeans(WttsEntity.class);
		System.out.println("转为实体对象：\n" + Arrays.toString(userList.toArray()));
	}
//		--------------------- 
//		作者：风中漫步者 
//		来源：CSDN 
//		原文：https://blog.csdn.net/qwqw3333333/article/details/84333510 
//		版权声明：本文为博主原创文章，转载请附上博文链接！

}
