# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# 디버깅 편의를 위해 라인 번호 보존. (ANR/크래시 리포트에서 의미 있는 스택트레이스 확보)
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

##---------------- kotlinx.serialization ----------------
# 본 프로젝트는 DTO / Navigation3 의 GenericNavKey / NavRoute 직렬화 등에 광범위하게
# kotlinx.serialization 을 사용한다. 직렬화 코드는 컴파일 시 생성된 *$Companion.serializer()
# 또는 object 의 INSTANCE.serializer() 를 리플렉션으로 호출하기 때문에 아래 규칙들이 없으면
# release 빌드에서 SerializationException 이 발생한다.

# @Serializable / @Polymorphic 등 런타임 어노테이션 보존.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# @Serializable 표시된 클래스의 Companion 필드 보존.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Companion 객체의 serializer() 메서드 보존.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# @Serializable object 의 INSTANCE.serializer() 보존.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# 컴파일러가 생성하는 $$serializer 내부 클래스 자체와 그 INSTANCE 필드 보존.
-keepclasseswithmembers class **$$serializer {
    *** INSTANCE;
}
