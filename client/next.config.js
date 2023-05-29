/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination:
          "https://port-0-codemy-7e6o2clhzvliku.sel4.cloudtype.app/:path*",
      },
    ];
  },
};

module.exports = nextConfig;
