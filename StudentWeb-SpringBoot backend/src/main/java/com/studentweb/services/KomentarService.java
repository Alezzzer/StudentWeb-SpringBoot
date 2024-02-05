package com.studentweb.services;

import com.studentweb.model.Komentar;

public interface KomentarService {

	Komentar createComment(Komentar comment,String username,Integer postid);
	void deleteComment(String username,Integer commentid);
}
