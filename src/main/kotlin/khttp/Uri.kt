package khttp

import java.util.regex.Pattern

data class Uri(val scheme: String? = null,
               val authority: Authority? = null,
               val path: List<String>? = null,
               val query: Map<String, String>? = null,
               val fragment: String? = null) {
    override fun toString(): String {
        var sb = StringBuilder(256)
        scheme?.let {
            sb.append(it, ":")
        }
        authority?.let {
            sb.append("//", authority)
        }
        path?.let {
            sb.append(it.joinToString(separator = "/", prefix = "/", transform = {
                it.uriEncode()
            }))
        }
        query?.let {
            sb.append(query.joinToString(prefix = "?", separator = "&", kvSeparator = "=", postfix = "", keyTransform = {
                it.uriEncode()
            }, valueTransform = {
                it.uriEncode()
            }))
        }
        fragment?.let {
            sb.append(fragment)
        }
        return sb.toString()
    }

    class Builder {
        var scheme: String? = null
        var authority: Authority? = null
        var path: List<String>? = null
        var query: Map<String, String>? = null
        var fragment: String? = null
    }

    companion object {
        private val uriPattern = Pattern.compile("""(?:(?<scheme>[a-z][a-z\+\-\.]+):)?(?:\/\/(?:(?<username>[A-Za-z0-9\-\._~%]+)(?::(?<password>[A-Za-z0-9\-\._~%]+))?@)?(?<host>[A-Za-z0-9\-\._~%]+)(?::(?<port>[0-9]+)))?(?<path>(?:\/?[A-Za-z0-9\-\._~%]+)?(?:\/[A-Za-z0-9\-\._~%]+)*|\/)?(?:\?(?<query>(?:[A-Za-z0-9\-\._~%]+=[A-Za-z0-9\-\._~%]+)?(?:&[A-Za-z0-9\-\._~%]+=[A-Za-z0-9\-\._~%]+)*))?(?:#(?<fragment>[A-Za-z0-9\-\._~%]+))?""")

        fun build(init: Builder.() -> Unit): Uri {
            val builder = Uri.Builder()
            builder.init()
            return Uri(builder.scheme, builder.authority, builder.path, builder.query, builder.fragment)
        }
        fun fromString(uri: String): Uri {
            return Uri.build {
                val matcher = uriPattern.matcher(uri)
                scheme = matcher.maybeGroup("scheme")

                val hostString = matcher.maybeGroup("host")
                hostString?.let {
                    authority = Authority.build {
                        username = matcher.maybeGroup("username")
                        password = matcher.maybeGroup("password")
                        host = it
                        port = matcher.maybeGroup("port")?.toInt()
                    }

                }
                path = matcher.maybeGroup("path")?.split("/")
                query = null //matcher.maybeGroup("query")
                fragment = matcher.maybeGroup("fragment")
            }
        }
    }
}

data class Authority(val auth: Authentication? = null, val host: String, val port: Int? = null) {
    override fun toString(): String {
        val sb = StringBuilder(64)

        auth?.let {
            sb.append(auth, "@")
        }
        sb.append(host.uriEncode())
        port?.let {
            sb.append(":", port)
        }
        return sb.toString()
    }

    class Builder {
        var username: String? = null
        var password: String? = null
        lateinit var host: String
        var port: Int? = null
    }

    companion object {
        fun build(init: Authority.Builder.() -> Unit): Authority {
            val builder = Authority.Builder()
            builder.init()
            return Authority(
                    if (builder.username != null) {
                        Authentication(builder.username ?: "", builder.password)
                    } else null, builder.host, builder.port)
        }
    }
}

data class Authentication(val username: String, val password: String? = null) {
    override fun toString(): String {
        val sb = StringBuilder(32)
        sb.append(username.uriEncode())
        password?.let {
            sb.append(":", it.uriEncode())
        }
        return sb.toString()
    }
}