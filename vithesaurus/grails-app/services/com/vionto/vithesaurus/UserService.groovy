package com.vionto.vithesaurus

import java.security.MessageDigest;

class UserService {
    String md5sum(String str) {
        // a pseudo-random salt:
        final String salt = "hi234z2ejgrr97otw4ujzt4wt7jtsr4975FERedefef"
        str = str + "/" + salt
        MessageDigest md = MessageDigest.getInstance("MD5")
        md.update(str.getBytes(), 0, str.length())
        byte[] md5sum = md.digest()
        BigInteger bigInt = new BigInteger(1, md5sum)
        return bigInt.toString(16)
    }
}